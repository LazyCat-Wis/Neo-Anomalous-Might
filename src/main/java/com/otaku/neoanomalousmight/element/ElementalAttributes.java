package com.otaku.neoanomalousmight.element;

import java.util.HashMap;
import java.util.Map;

/**
 * 元素属性类
 * 管理元素之间的相克关系和元素对角色属性的影响
 */
public class ElementalAttributes {
    
    // 元素相克关系映射：攻击者元素 -> 被攻击者元素 -> 伤害倍率
    private static final Map<ElementType, Map<ElementType, Float>> ELEMENTAL_DAMAGE_MULTIPLIERS = new HashMap<>();
    
    // 元素防御影响映射：攻击者元素 -> 被攻击者元素 -> 防御变化百分比
    private static final Map<ElementType, Map<ElementType, Float>> ELEMENTAL_DEFENSE_MULTIPLIERS = new HashMap<>();
    
    // 元素闪避影响映射：攻击者元素 -> 被攻击者元素 -> 闪避变化百分比
    private static final Map<ElementType, Map<ElementType, Float>> ELEMENTAL_EVASION_MULTIPLIERS = new HashMap<>();
    
    // 元素对角色基础属性的影响
    private static final Map<ElementType, Map<String, Float>> ELEMENTAL_BASE_ATTRIBUTE_BONUSES = new HashMap<>();
    
    static {
        initializeElementalRelations();
        initializeElementalAttributeBonuses();
    }
    
    /**
     * 初始化元素相克关系
     */
    private static void initializeElementalRelations() {
        // 初始化所有元素的映射
        for (ElementType attacker : ElementType.values()) {
            ELEMENTAL_DAMAGE_MULTIPLIERS.put(attacker, new HashMap<>());
            ELEMENTAL_DEFENSE_MULTIPLIERS.put(attacker, new HashMap<>());
            ELEMENTAL_EVASION_MULTIPLIERS.put(attacker, new HashMap<>());
            
            for (ElementType defender : ElementType.values()) {
                ELEMENTAL_DAMAGE_MULTIPLIERS.get(attacker).put(defender, 1.0f);
                ELEMENTAL_DEFENSE_MULTIPLIERS.get(attacker).put(defender, 0.0f);
                ELEMENTAL_EVASION_MULTIPLIERS.get(attacker).put(defender, 0.0f);
            }
        }
        
        // 设置元素相克关系
        
        // 火克制草、冰、水：火属性方伤害+15%
        setElementalRelation(ElementType.FIRE, ElementType.GRASS, 1.15f, 0.0f, 0.0f);
        setElementalRelation(ElementType.FIRE, ElementType.ICE, 1.15f, 0.0f, 0.0f);
        setElementalRelation(ElementType.FIRE, ElementType.WATER, 1.15f, 0.0f, 0.0f);
        
        // 水克制岩：水属性方伤害+10%，岩属性方防御-10%
        setElementalRelation(ElementType.WATER, ElementType.EARTH, 1.10f, -0.10f, 0.0f);
        
        // 雷克制水：雷属性方伤害+10%，水属性方防御-10%
        setElementalRelation(ElementType.THUNDER, ElementType.WATER, 1.10f, -0.10f, 0.0f);
        
        // 风增强火：火属性方伤害+15%，防御-10%
        setElementalRelation(ElementType.FIRE, ElementType.WIND, 1.15f, -0.10f, 0.0f);
        
        // 水增强雷：雷属性方伤害+10%，防御-5%，闪避-5%
        setElementalRelation(ElementType.THUNDER, ElementType.WATER, 1.10f, -0.05f, -0.05f);
        
        // 岩克制雷：岩属性方防御+10%；雷属性方伤害-10%
        setElementalRelation(ElementType.THUNDER, ElementType.EARTH, 0.90f, 0.10f, 0.0f);
        
        // 冰克制草：草属性方伤害-5%，防御-5%，闪避-5%，冰属性方伤害+10%
        setElementalRelation(ElementType.ICE, ElementType.GRASS, 1.10f, 0.0f, 0.0f);
        setElementalRelation(ElementType.GRASS, ElementType.ICE, 0.95f, -0.05f, -0.05f);
    }
    
    /**
     * 初始化元素对角色基础属性的影响
     */
    private static void initializeElementalAttributeBonuses() {
        for (ElementType element : ElementType.values()) {
            ELEMENTAL_BASE_ATTRIBUTE_BONUSES.put(element, new HashMap<>());
        }
        
        // 火系角色伤害+10%
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.FIRE).put("damage", 0.10f);
        
        // 水系角色法力+10%
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.WATER).put("mana", 0.10f);
        
        // 风系角色闪避几率和移速+5%
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.WIND).put("evasion", 0.05f);
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.WIND).put("movementSpeed", 0.05f);
        
        // 雷系角色暴击几率+10%，暴击伤害+50
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.THUNDER).put("critChance", 0.10f);
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.THUNDER).put("critDamage", 50.0f);
        
        // 草基础生命值+10%
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.GRASS).put("health", 0.10f);
        
        // 冰伤害+5%，闪避-5%，防御-5%，移速+10%
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.ICE).put("damage", 0.05f);
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.ICE).put("evasion", -0.05f);
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.ICE).put("defense", -0.05f);
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.ICE).put("movementSpeed", 0.10f);
        
        // 岩防御+15%，移速-2%，闪避几率-4%
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.EARTH).put("defense", 0.15f);
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.EARTH).put("movementSpeed", -0.02f);
        ELEMENTAL_BASE_ATTRIBUTE_BONUSES.get(ElementType.EARTH).put("evasion", -0.04f);
    }
    
    /**
     * 设置元素关系
     * @param attacker 攻击者元素类型
     * @param defender 被攻击者元素类型
     * @param damageMultiplier 伤害倍率
     * @param defenseChange 防御变化百分比
     * @param evasionChange 闪避变化百分比
     */
    private static void setElementalRelation(ElementType attacker, ElementType defender, float damageMultiplier, float defenseChange, float evasionChange) {
        ELEMENTAL_DAMAGE_MULTIPLIERS.get(attacker).put(defender, damageMultiplier);
        ELEMENTAL_DEFENSE_MULTIPLIERS.get(attacker).put(defender, defenseChange);
        ELEMENTAL_EVASION_MULTIPLIERS.get(attacker).put(defender, evasionChange);
    }
    
    /**
     * 获取元素伤害倍率
     * @param attacker 攻击者元素类型
     * @param defender 被攻击者元素类型
     * @return 伤害倍率
     */
    public static float getDamageMultiplier(ElementType attacker, ElementType defender) {
        return ELEMENTAL_DAMAGE_MULTIPLIERS.getOrDefault(attacker, new HashMap<>()).getOrDefault(defender, 1.0f);
    }
    
    /**
     * 获取元素防御变化百分比
     * @param attacker 攻击者元素类型
     * @param defender 被攻击者元素类型
     * @return 防御变化百分比
     */
    public static float getDefenseChange(ElementType attacker, ElementType defender) {
        return ELEMENTAL_DEFENSE_MULTIPLIERS.getOrDefault(attacker, new HashMap<>()).getOrDefault(defender, 0.0f);
    }
    
    /**
     * 获取元素闪避变化百分比
     * @param attacker 攻击者元素类型
     * @param defender 被攻击者元素类型
     * @return 闪避变化百分比
     */
    public static float getEvasionChange(ElementType attacker, ElementType defender) {
        return ELEMENTAL_EVASION_MULTIPLIERS.getOrDefault(attacker, new HashMap<>()).getOrDefault(defender, 0.0f);
    }
    
    /**
     * 获取元素对基础属性的影响
     * @param element 元素类型
     * @param attribute 属性名称
     * @return 属性影响值
     */
    public static float getElementalAttributeBonus(ElementType element, String attribute) {
        return ELEMENTAL_BASE_ATTRIBUTE_BONUSES.getOrDefault(element, new HashMap<>()).getOrDefault(attribute, 0.0f);
    }
}