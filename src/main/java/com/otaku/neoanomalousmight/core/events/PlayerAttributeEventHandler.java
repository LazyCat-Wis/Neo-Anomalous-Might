package com.otaku.neoanomalousmight.core.events;

import com.otaku.neoanomalousmight.capability.player.PlayerRoleProvider;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.otaku.neoanomalousmight.Neo_Anomalous_Might.MOD_ID;

/**
 * 玩家属性事件处理器
 * 将角色属性直接附加到玩家实体上
 */
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerAttributeEventHandler {
    
    // 唯一标识符，用于属性修饰符
    private static final UUID HEALTH_MODIFIER_ID = UUID.fromString("5f2e7d63-8a3c-4b2d-9c1e-3a7f8b4d5e6f");
    private static final UUID ATTACK_DAMAGE_MODIFIER_ID = UUID.fromString("a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d");
    private static final UUID MOVEMENT_SPEED_MODIFIER_ID = UUID.fromString("f1e2d3c4-b5a6-7f8e-9d0c-1b2a3f4e5d6c");
    private static final UUID ARMOR_MODIFIER_ID = UUID.fromString("3a4b5c6d-7e8f-9a0b-1c2d-3e4f5a6b7c8d");
    
    /**
     * 当玩家数据从NBT加载完成时应用属性
     * 确保角色属性在玩家进入游戏存档时就已经加载
     */
    @SubscribeEvent
    public static void onPlayerLoadFromFile(PlayerEvent.LoadFromFile event) {
        Player player = event.getEntity();
        applyRoleAttributes(player);
    }

    /**
     * 当玩家加入世界时应用属性
     */
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            applyRoleAttributes(player);
        }
    }
    
    /**
     * 当玩家重新生成时应用属性
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player newPlayer = event.getEntity();
            applyRoleAttributes(newPlayer);
        }
    }
    
    /**
     * 当玩家数据加载时应用属性
     */
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        applyRoleAttributes(player);
    }
    
    /**
     * 将角色属性应用到玩家实体
     */
    public static void applyRoleAttributes(Player player) {
        player.getCapability(PlayerRoleProvider.PLAYER_ROLE_CAPABILITY).ifPresent(roleCapability -> {
            if (roleCapability.hasSelectedRole()) {
                // 应用生命值
                applyAttributeModifier(player, Attributes.MAX_HEALTH, HEALTH_MODIFIER_ID, 
                    roleCapability.getMaxHealth() - 20.0f, AttributeModifier.Operation.ADDITION);
                
                // 应用攻击力
                applyAttributeModifier(player, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER_ID, 
                    roleCapability.getAttack() - 1.0f, AttributeModifier.Operation.ADDITION);
                
                // 应用移动速度
                applyAttributeModifier(player, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER_ID, 
                    roleCapability.getMovementSpeed() - 1.0f, AttributeModifier.Operation.MULTIPLY_BASE);
                
                // 应用防御力
                applyAttributeModifier(player, Attributes.ARMOR, ARMOR_MODIFIER_ID, 
                    roleCapability.getDefense(), AttributeModifier.Operation.ADDITION);
                
                // 立即更新玩家生命值以反映最大生命值的变化
                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                }
            } else {
                // 如果没有选择角色，移除所有属性修饰符
                removeAttributeModifier(player, Attributes.MAX_HEALTH, HEALTH_MODIFIER_ID);
                removeAttributeModifier(player, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER_ID);
                removeAttributeModifier(player, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER_ID);
                removeAttributeModifier(player, Attributes.ARMOR, ARMOR_MODIFIER_ID);
            }
        });
    }
    
    /**
     * 应用属性修饰符
     */
    private static void applyAttributeModifier(Player player, Attribute attribute, UUID uuid, double value, AttributeModifier.Operation operation) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        if (attributeInstance != null) {
            // 先移除旧的修饰符
            attributeInstance.removeModifier(uuid);
            
            // 添加新的修饰符
            AttributeModifier modifier = new AttributeModifier(uuid, MOD_ID + ":role_attribute", value, operation);
            attributeInstance.addPermanentModifier(modifier);
        }
    }
    
    /**
     * 移除属性修饰符
     */
    private static void removeAttributeModifier(Player player, Attribute attribute, UUID uuid) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        if (attributeInstance != null) {
            attributeInstance.removeModifier(uuid);
        }
    }
}