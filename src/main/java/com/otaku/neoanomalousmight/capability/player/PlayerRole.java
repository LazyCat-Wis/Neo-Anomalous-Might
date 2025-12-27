package com.otaku.neoanomalousmight.capability.player;

import com.otaku.neoanomalousmight.element.ElementalAttributes;
import com.otaku.neoanomalousmight.role.Role;
import com.otaku.neoanomalousmight.role.ModRoles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * 角色能力实现类
 * 处理玩家角色的实际数据和逻辑
 */
public class PlayerRole implements IPlayerRole {
    
    private Role selectedRole = null;
    private int level = 1;
    private int experience = 0;
    
    // 升级所需经验值公式系数
    private static final int BASE_EXPERIENCE = 100;
    private static final float EXPERIENCE_MULTIPLIER = 1.5f;
    
    /**
     * 获取玩家选择的角色
     * @return 角色对象，如果未选择则返回null
     */
    @Nullable
    @Override
    public Role getSelectedRole() {
        return selectedRole;
    }
    
    /**
     * 设置玩家选择的角色
     * @param role 角色对象
     */
    @Override
    public void setSelectedRole(Role role) {
        this.selectedRole = role;
    }
    
    /**
     * 检查玩家是否已经选择了角色
     * @return 如果已经选择角色则返回true，否则返回false
     */
    @Override
    public boolean hasSelectedRole() {
        return selectedRole != null;
    }
    
    /**
     * 获取玩家的等级
     * @return 玩家等级
     */
    @Override
    public int getLevel() {
        return level;
    }
    
    /**
     * 设置玩家的等级
     * @param level 玩家等级
     */
    @Override
    public void setLevel(int level) {
        this.level = Math.max(1, level);
    }
    
    /**
     * 获取玩家的经验值
     * @return 玩家经验值
     */
    @Override
    public int getExperience() {
        return experience;
    }
    
    /**
     * 设置玩家的经验值
     * @param experience 玩家经验值
     */
    @Override
    public void setExperience(int experience) {
        this.experience = Math.max(0, experience);
    }
    
    /**
     * 增加玩家的经验值
     * @param experience 要增加的经验值
     * @return 是否升级
     */
    @Override
    public boolean addExperience(int experience) {
        if (experience <= 0) return false;
        
        this.experience += experience;
        boolean leveledUp = false;
        
        // 检查是否可以升级
        while (this.experience >= getRequiredExperience(level)) {
            this.experience -= getRequiredExperience(level);
            level++;
            leveledUp = true;
        }
        
        return leveledUp;
    }
    
    /**
     * 获取升级所需的经验值
     * @param level 当前等级
     * @return 升级所需经验值
     */
    private int getRequiredExperience(int level) {
        return (int) (BASE_EXPERIENCE * Math.pow(EXPERIENCE_MULTIPLIER, level - 1));
    }
    
    /**
     * 获取玩家的最大生命值（考虑角色基础属性和等级）
     * @return 最大生命值
     */
    @Override
    public float getMaxHealth() {
        if (selectedRole == null) return 20.0f; // 默认生命值
        
        float baseHealth = selectedRole.getBaseHealth();
        float levelBonus = baseHealth * 0.05f * (level - 1); // 每级增加5%
        float elementBonus = baseHealth * ElementalAttributes.getElementalAttributeBonus(selectedRole.getElementType(), "health");
        
        return baseHealth + levelBonus + elementBonus;
    }
    
    /**
     * 获取玩家的攻击力（考虑角色基础属性和等级）
     * @return 攻击力
     */
    @Override
    public float getAttack() {
        if (selectedRole == null) return 1.0f; // 默认攻击力
        
        float baseAttack = selectedRole.getBaseAttack();
        float levelBonus = baseAttack * 0.07f * (level - 1); // 每级增加7%
        float elementBonus = baseAttack * ElementalAttributes.getElementalAttributeBonus(selectedRole.getElementType(), "damage");
        
        return baseAttack + levelBonus + elementBonus;
    }
    
    /**
     * 获取玩家的防御力（考虑角色基础属性和等级）
     * @return 防御力
     */
    @Override
    public float getDefense() {
        if (selectedRole == null) return 0.0f; // 默认防御力
        
        float baseDefense = selectedRole.getBaseDefense();
        float levelBonus = baseDefense * 0.06f * (level - 1); // 每级增加6%
        float elementBonus = baseDefense * ElementalAttributes.getElementalAttributeBonus(selectedRole.getElementType(), "defense");
        
        return baseDefense + levelBonus + elementBonus;
    }
    
    /**
     * 获取玩家的最大法力值（考虑角色基础属性和等级）
     * @return 最大法力值
     */
    @Override
    public float getMaxMana() {
        if (selectedRole == null) return 0.0f; // 默认法力值
        
        float baseMana = selectedRole.getBaseMana();
        float levelBonus = baseMana * 0.08f * (level - 1); // 每级增加8%
        float elementBonus = baseMana * ElementalAttributes.getElementalAttributeBonus(selectedRole.getElementType(), "mana");
        
        return baseMana + levelBonus + elementBonus;
    }
    
    /**
     * 获取玩家的暴击几率（考虑角色基础属性和等级）
     * @return 暴击几率 (0.0-1.0)
     */
    @Override
    public float getCritChance() {
        if (selectedRole == null) return 0.0f; // 默认暴击几率
        
        float baseCritChance = selectedRole.getBaseCritChance();
        float levelBonus = 0.005f * (level - 1); // 每级增加0.5%
        float elementBonus = ElementalAttributes.getElementalAttributeBonus(selectedRole.getElementType(), "critChance");
        
        return Math.min(1.0f, baseCritChance + levelBonus + elementBonus);
    }
    
    /**
     * 获取玩家的暴击伤害（考虑角色基础属性和等级）
     * @return 暴击伤害倍率 (1.0+)
     */
    @Override
    public float getCritDamage() {
        if (selectedRole == null) return 1.0f; // 默认暴击伤害
        
        float baseCritDamage = selectedRole.getBaseCritDamage();
        float levelBonus = 0.02f * (level - 1); // 每级增加2%
        float elementBonus = ElementalAttributes.getElementalAttributeBonus(selectedRole.getElementType(), "critDamage");
        
        return baseCritDamage + levelBonus + elementBonus;
    }
    
    /**
     * 获取玩家的闪避几率（考虑角色基础属性和等级）
     * @return 闪避几率 (0.0-1.0)
     */
    @Override
    public float getEvasion() {
        if (selectedRole == null) return 0.0f; // 默认闪避几率
        
        float baseEvasion = selectedRole.getBaseEvasion();
        float levelBonus = 0.003f * (level - 1); // 每级增加0.3%
        float elementBonus = ElementalAttributes.getElementalAttributeBonus(selectedRole.getElementType(), "evasion");
        
        return Math.min(1.0f, Math.max(0.0f, baseEvasion + levelBonus + elementBonus));
    }
    
    /**
     * 获取玩家的移动速度（考虑角色基础属性和等级）
     * @return 移动速度倍率 (1.0+)
     */
    @Override
    public float getMovementSpeed() {
        if (selectedRole == null) return 1.0f; // 默认移动速度
        
        float baseMovementSpeed = selectedRole.getBaseMovementSpeed();
        float levelBonus = 0.01f * (level - 1); // 每级增加1%
        float elementBonus = ElementalAttributes.getElementalAttributeBonus(selectedRole.getElementType(), "movementSpeed");
        
        return Math.max(0.1f, baseMovementSpeed + levelBonus + elementBonus);
    }
    
    /**
     * 将角色数据保存到NBT
     * @return 包含角色数据的NBT标签
     */
    @Override
    public CompoundTag saveNBTData() {
        CompoundTag nbt = new CompoundTag();
        
        // 保存角色数据
        if (selectedRole != null) {
            ResourceLocation roleId = selectedRole.getRegistryName();
            if (roleId != null) {
                nbt.putString("Role", roleId.toString());
            }
        }
        
        // 保存等级和经验
        nbt.putInt("Level", level);
        nbt.putInt("Experience", experience);
        
        return nbt;
    }
    
    /**
     * 从NBT加载角色数据
     * @param nbt 包含角色数据的NBT标签
     */
    @Override
    public void loadNBTData(CompoundTag nbt) {
        // 加载角色数据
        if (nbt.contains("Role")) {
            String roleIdStr = nbt.getString("Role");
            ResourceLocation roleId = ResourceLocation.tryParse(roleIdStr);
            if (roleId != null) {
                // 使用ModRoles.getRole方法获取角色
                selectedRole = ModRoles.getRole(roleId);
            }
        }
        
        // 加载等级和经验
        if (nbt.contains("Level")) {
            level = nbt.getInt("Level");
        }
        
        if (nbt.contains("Experience")) {
            experience = nbt.getInt("Experience");
        }
    }
}