package com.otaku.neoanomalousmight.element;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

/**
 * 元素交互类
 * 处理元素之间的相互作用和效果
 */
public class ElementalInteraction {
    
    /**
     * 应用元素效果到目标实体
     * @param attacker 攻击者
     * @param target 目标实体
     * @param attackerElement 攻击者元素类型
     * @param targetElement 目标元素类型
     */
    public static void applyElementalEffect(LivingEntity attacker, LivingEntity target, ElementType attackerElement, ElementType targetElement) {
        // 根据攻击者和目标的元素类型应用不同的效果
        switch (attackerElement) {
            case FIRE:
                applyFireEffect(target, targetElement);
                break;
            case WATER:
                applyWaterEffect(target, targetElement);
                break;
            case WIND:
                applyWindEffect(target, targetElement);
                break;
            case THUNDER:
                applyThunderEffect(target, targetElement);
                break;
            case GRASS:
                applyGrassEffect(target, targetElement);
                break;
            case ICE:
                applyIceEffect(target, targetElement);
                break;
            case EARTH:
                applyEarthEffect(target, targetElement);
                break;
            default:
                // 无元素效果
                break;
        }
    }
    
    /**
     * 应用火元素效果
     * @param target 目标实体
     * @param targetElement 目标元素类型
     */
    private static void applyFireEffect(LivingEntity target, ElementType targetElement) {
        // 火元素基础效果：燃烧
        target.setSecondsOnFire(5);
        
        // 根据目标元素类型增强效果
        if (targetElement == ElementType.GRASS || targetElement == ElementType.ICE) {
            // 对草和冰元素目标，燃烧时间加倍
            target.setSecondsOnFire(10);
        } else if (targetElement == ElementType.WIND) {
            // 风元素增强火元素效果
            target.setSecondsOnFire(15);
        }
    }
    
    /**
     * 应用水元素效果
     * @param target 目标实体
     * @param targetElement 目标元素类型
     */
    private static void applyWaterEffect(LivingEntity target, ElementType targetElement) {
        // 水元素基础效果：减速
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
        
        // 根据目标元素类型增强效果
        if (targetElement == ElementType.EARTH) {
            // 对岩元素目标，增加缓慢效果等级
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 150, 2));
        } else if (targetElement == ElementType.THUNDER) {
            // 水元素增强雷元素效果
            applyThunderEffect(target, ElementType.WATER);
        }
    }
    
    /**
     * 应用风元素效果
     * @param target 目标实体
     * @param targetElement 目标元素类型
     */
    private static void applyWindEffect(LivingEntity target, ElementType targetElement) {
        // 风元素基础效果：击退
        target.push(0, 0.5, 0);
        
        // 根据目标元素类型增强效果
        if (targetElement == ElementType.FIRE) {
            // 风元素增强火元素效果
            applyFireEffect(target, ElementType.WIND);
        }
    }
    
    /**
     * 应用雷元素效果
     * @param target 目标实体
     * @param targetElement 目标元素类型
     */
    private static void applyThunderEffect(LivingEntity target, ElementType targetElement) {
        // 雷元素基础效果：感电
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1));
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 50, 0));
        
        // 根据目标元素类型增强效果
        if (targetElement == ElementType.WATER) {
            // 对水元素目标，增加感电效果
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 150, 2));
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
        } else if (targetElement == ElementType.EARTH) {
            // 岩元素减弱雷元素效果
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 50, 0));
        }
    }
    
    /**
     * 应用草元素效果
     * @param target 目标实体
     * @param targetElement 目标元素类型
     */
    private static void applyGrassEffect(LivingEntity target, ElementType targetElement) {
        // 草元素基础效果：回复
        target.heal(2.0F);
        
        // 根据目标元素类型调整效果
        if (targetElement == ElementType.FIRE) {
            // 火元素克制草元素，减少回复效果
            target.heal(1.0F);
        } else if (targetElement == ElementType.ICE) {
            // 冰元素克制草元素，没有回复效果
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 50, 0));
        }
    }
    
    /**
     * 应用冰元素效果
     * @param target 目标实体
     * @param targetElement 目标元素类型
     */
    private static void applyIceEffect(LivingEntity target, ElementType targetElement) {
        // 冰元素基础效果：冰冻减速
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 150, 2));
        
        // 根据目标元素类型增强效果
        if (targetElement == ElementType.GRASS) {
            // 对草元素目标，增加冰冻效果
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 3));
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
        } else if (targetElement == ElementType.FIRE) {
            // 火元素减弱冰元素效果
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 1));
        }
    }
    
    /**
     * 应用岩元素效果
     * @param target 目标实体
     * @param targetElement 目标元素类型
     */
    private static void applyEarthEffect(LivingEntity target, ElementType targetElement) {
        // 岩元素基础效果：增加防御
        target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 1));
        
        // 根据目标元素类型调整效果
        if (targetElement == ElementType.THUNDER) {
            // 岩元素克制雷元素，增强防御效果
            target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 150, 2));
        } else if (targetElement == ElementType.WATER) {
            // 水元素克制岩元素，减弱防御效果
            target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 50, 0));
        }
    }
    
    /**
     * 计算元素反应伤害
     * @param baseDamage 基础伤害
     * @param attackerElement 攻击者元素类型
     * @param targetElement 目标元素类型
     * @return 计算后的伤害
     */
    public static float calculateElementalReactionDamage(float baseDamage, ElementType attackerElement, ElementType targetElement) {
        // 获取元素伤害倍率
        float damageMultiplier = ElementalAttributes.getDamageMultiplier(attackerElement, targetElement);
        
        // 计算最终伤害
        return baseDamage * damageMultiplier;
    }
}