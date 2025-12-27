package com.otaku.neoanomalousmight.role;

import com.otaku.neoanomalousmight.element.ElementType;
import net.minecraft.resources.ResourceLocation;


/**
 * 角色类
 * 定义了角色的基本属性和信息
 */
public class Role {
    
    private final ResourceLocation registryName;
    private final String name;
    private final String description;
    private final ElementType elementType;
    private final float baseHealth;
    private final float baseAttack;
    private final float baseDefense;
    private final float baseMana;
    private final float baseCritChance;
    private final float baseCritDamage;
    private final float baseEvasion;
    private final float baseMovementSpeed;
    
    /**
     * 构造函数
     * @param registryName 注册名称
     * @param name 角色名称
     * @param description 角色描述
     * @param elementType 元素类型
     * @param baseHealth 基础生命值
     * @param baseAttack 基础攻击力
     * @param baseDefense 基础防御力
     * @param baseMana 基础法力值
     * @param baseCritChance 基础暴击几率
     * @param baseCritDamage 基础暴击伤害
     * @param baseEvasion 基础闪避几率
     * @param baseMovementSpeed 基础移动速度
     */
    public Role(ResourceLocation registryName, String name, String description, ElementType elementType,
               float baseHealth, float baseAttack, float baseDefense, float baseMana,
               float baseCritChance, float baseCritDamage, float baseEvasion, float baseMovementSpeed) {
        this.registryName = registryName;
        this.name = name;
        this.description = description;
        this.elementType = elementType;
        this.baseHealth = baseHealth;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseMana = baseMana;
        this.baseCritChance = baseCritChance;
        this.baseCritDamage = baseCritDamage;
        this.baseEvasion = baseEvasion;
        this.baseMovementSpeed = baseMovementSpeed;
    }
    
    /**
     * 获取角色名称
     * @return 角色名称
     */
    public String getName() {
        return name;
    }
    
    /**
     * 获取角色描述
     * @return 角色描述
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 获取元素类型
     * @return 元素类型
     */
    public ElementType getElementType() {
        return elementType;
    }
    
    /**
     * 获取基础生命值
     * @return 基础生命值
     */
    public float getBaseHealth() {
        return baseHealth;
    }
    
    /**
     * 获取基础攻击力
     * @return 基础攻击力
     */
    public float getBaseAttack() {
        return baseAttack;
    }
    
    /**
     * 获取注册名称
     * @return 注册名称
     */
    public ResourceLocation getRegistryName() {
        return registryName;
    }
    
    /**
     * 获取基础防御力
     * @return 基础防御力
     */
    public float getBaseDefense() {
        return baseDefense;
    }
    
    /**
     * 获取基础法力值
     * @return 基础法力值
     */
    public float getBaseMana() {
        return baseMana;
    }
    
    /**
     * 获取基础暴击几率
     * @return 基础暴击几率
     */
    public float getBaseCritChance() {
        return baseCritChance;
    }
    
    /**
     * 获取基础暴击伤害
     * @return 基础暴击伤害
     */
    public float getBaseCritDamage() {
        return baseCritDamage;
    }
    
    /**
     * 获取基础闪避几率
     * @return 基础闪避几率
     */
    public float getBaseEvasion() {
        return baseEvasion;
    }
    
    /**
     * 获取基础移动速度
     * @return 基础移动速度
     */
    public float getBaseMovementSpeed() {
        return baseMovementSpeed;
    }
    
    /**
     * 获取角色的翻译键
     * @return 翻译键
     */
    public String getTranslationKey() {
        return "role.neoanomalousmight." + registryName.getPath();
    }
    
    @Override
    public String toString() {
        return name + " (" + elementType.getChineseName() + ")";
    }
}