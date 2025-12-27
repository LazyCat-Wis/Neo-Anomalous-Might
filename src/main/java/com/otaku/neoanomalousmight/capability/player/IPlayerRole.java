package com.otaku.neoanomalousmight.capability.player;

import com.otaku.neoanomalousmight.role.Role;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

/**
 * 角色能力接口
 * 定义了玩家角色能力的基本方法
 */
public interface IPlayerRole {
    
    /**
     * 获取玩家选择的角色
     * @return 角色对象，如果未选择则返回null
     */
    @Nullable
    Role getSelectedRole();
    
    /**
     * 设置玩家选择的角色
     * @param role 角色对象
     */
    void setSelectedRole(Role role);
    
    /**
     * 检查玩家是否已经选择了角色
     * @return 如果已经选择角色则返回true，否则返回false
     */
    boolean hasSelectedRole();
    
    /**
     * 获取玩家的等级
     * @return 玩家等级
     */
    int getLevel();
    
    /**
     * 设置玩家的等级
     * @param level 玩家等级
     */
    void setLevel(int level);
    
    /**
     * 获取玩家的经验值
     * @return 玩家经验值
     */
    int getExperience();
    
    /**
     * 设置玩家的经验值
     * @param experience 玩家经验值
     */
    void setExperience(int experience);
    
    /**
     * 增加玩家的经验值
     * @param experience 要增加的经验值
     * @return 是否升级
     */
    boolean addExperience(int experience);
    
    /**
     * 获取玩家的最大生命值（考虑角色基础属性和等级）
     * @return 最大生命值
     */
    float getMaxHealth();
    
    /**
     * 获取玩家的攻击力（考虑角色基础属性和等级）
     * @return 攻击力
     */
    float getAttack();
    
    /**
     * 获取玩家的防御力（考虑角色基础属性和等级）
     * @return 防御力
     */
    float getDefense();
    
    /**
     * 获取玩家的最大法力值（考虑角色基础属性和等级）
     * @return 最大法力值
     */
    float getMaxMana();
    
    /**
     * 获取玩家的暴击几率（考虑角色基础属性和等级）
     * @return 暴击几率 (0.0-1.0)
     */
    float getCritChance();
    
    /**
     * 获取玩家的暴击伤害（考虑角色基础属性和等级）
     * @return 暴击伤害倍率 (1.0+)
     */
    float getCritDamage();
    
    /**
     * 获取玩家的闪避几率（考虑角色基础属性和等级）
     * @return 闪避几率 (0.0-1.0)
     */
    float getEvasion();
    
    /**
     * 获取玩家的移动速度（考虑角色基础属性和等级）
     * @return 移动速度倍率 (1.0+)
     */
    float getMovementSpeed();
    
    /**
     * 将角色数据保存到NBT
     * @return 包含角色数据的NBT标签
     */
    CompoundTag saveNBTData();
    
    /**
     * 从NBT加载角色数据
     * @param nbt 包含角色数据的NBT标签
     */
    void loadNBTData(CompoundTag nbt);
}