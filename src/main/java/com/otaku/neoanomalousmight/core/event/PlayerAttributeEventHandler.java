package com.otaku.neoanomalousmight.core.event;

import com.otaku.neoanomalousmight.capability.player.PlayerRoleProvider;
import com.otaku.neoanomalousmight.role.Role;
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

import static com.otaku.neoanomalousmight.Neo_Anomalous_Might.LOGGER;
import static com.otaku.neoanomalousmight.Neo_Anomalous_Might.MOD_ID;

/**
 * 玩家属性事件处理器
 * 将角色属性直接附加到玩家实体上
 */
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerAttributeEventHandler {
    
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
                Role selectedRole = roleCapability.getRole();
                if (selectedRole != null) {
                    String roleId = selectedRole.getRegistryName().toString();
                    String playerUUID = player.getStringUUID();
                    
                    // 应用角色属性
                    applyRoleSpecificAttributes(player, selectedRole, playerUUID, roleId);
                }
            } else {
                // 如果没有选择角色，移除所有属性修饰符
                removeAllRoleModifiers(player);
                
                // 调试日志：移除属性值
                LOGGER.info("Removed role attributes from player {}", player.getName().getString());
            }
        });
    }
    
    /**
     * 应用角色特定的属性
     */
    private static void applyRoleSpecificAttributes(Player player, Role role, String playerUUID, String roleId) {
        // 应用生命值
        double maxHealthValue = role.getBaseHealth();
        UUID healthUUID = UUID.nameUUIDFromBytes((playerUUID + roleId + "_hp").getBytes());
        applyAttributeModifier(player, Attributes.MAX_HEALTH, healthUUID, 
            maxHealthValue - 20.0f, AttributeModifier.Operation.ADDITION, "role.bonus.health");
        
        // 应用攻击力
        double attackValue = role.getBaseAttack();
        UUID attackUUID = UUID.nameUUIDFromBytes((playerUUID + roleId + "_atk").getBytes());
        applyAttributeModifier(player, Attributes.ATTACK_DAMAGE, attackUUID, 
            attackValue - 1.0f, AttributeModifier.Operation.ADDITION, "role.bonus.attack");
        
        // 应用移动速度
        double movementSpeedValue = role.getBaseMovementSpeed();
        UUID speedUUID = UUID.nameUUIDFromBytes((playerUUID + roleId + "_speed").getBytes());
        applyAttributeModifier(player, Attributes.MOVEMENT_SPEED, speedUUID, 
            movementSpeedValue - 1.0f, AttributeModifier.Operation.MULTIPLY_BASE, "role.bonus.movement_speed");
        
        // 应用防御力
        double armorValue = role.getBaseDefense();
        UUID armorUUID = UUID.nameUUIDFromBytes((playerUUID + roleId + "_armor").getBytes());
        applyAttributeModifier(player, Attributes.ARMOR, armorUUID, 
            armorValue, AttributeModifier.Operation.ADDITION, "role.bonus.armor");
        
        // 立即更新玩家生命值以反映最大生命值的变化
        // 确保当前生命值不超过新的最大生命值
        float currentHealth = player.getHealth();
        float newMaxHealth = (float) maxHealthValue;
        if (currentHealth > newMaxHealth) {
            player.setHealth(newMaxHealth);
        } else if (currentHealth <= 0) {
            // 如果玩家当前生命值为0或负数，设置为新的最大生命值的一半
            player.setHealth(newMaxHealth / 2.0f);
        }
        
        // 调试日志：打印应用的属性值
        LOGGER.info("Applied role attributes to player {}: {}", player.getName().getString(), role.getName());
        LOGGER.info("  Max Health: {}", maxHealthValue);
        LOGGER.info("  Attack Damage: {}", attackValue);
        LOGGER.info("  Movement Speed: {}", movementSpeedValue);
        LOGGER.info("  Armor: {}", armorValue);
    }
    
    /**
     * 应用属性修饰符
     */
    private static void applyAttributeModifier(Player player, Attribute attribute, UUID uuid, double value, AttributeModifier.Operation operation, String modifierName) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        if (attributeInstance != null) {
            // 先移除旧的修饰符
            attributeInstance.removeModifier(uuid);
            
            // 添加新的修饰符
            AttributeModifier modifier = new AttributeModifier(uuid, MOD_ID + ":" + modifierName, value, operation);
            attributeInstance.addPermanentModifier(modifier);
            
            // 调试日志：打印修饰符详情
            LOGGER.info("Applied modifier {} to {}: {} (operation: {})", 
                modifierName, attribute.getDescriptionId(), value, operation.name());
            LOGGER.info("  New attribute value: {}", attributeInstance.getValue());
        } else {
            // 调试日志：属性实例不存在
            LOGGER.warn("Attribute instance for {} not found on player {}", 
                attribute.getDescriptionId(), player.getName().getString());
        }
    }
    
    /**
     * 移除所有角色相关的属性修饰符
     */
    private static void removeAllRoleModifiers(Player player) {
        player.getCapability(PlayerRoleProvider.PLAYER_ROLE_CAPABILITY).ifPresent(roleCapability -> {
            if (roleCapability.hasSelectedRole()) {
                Role selectedRole = roleCapability.getRole();
                if (selectedRole != null) {
                    String roleId = selectedRole.getRegistryName().toString();
                    String playerUUID = player.getStringUUID();
                    
                    // 移除生命值修饰符
                    UUID healthUUID = UUID.nameUUIDFromBytes((playerUUID + roleId + "_hp").getBytes());
                    removeAttributeModifier(player, Attributes.MAX_HEALTH, healthUUID);
                    
                    // 移攻击力修饰符
                    UUID attackUUID = UUID.nameUUIDFromBytes((playerUUID + roleId + "_atk").getBytes());
                    removeAttributeModifier(player, Attributes.ATTACK_DAMAGE, attackUUID);
                    
                    // 移移动速度修饰符
                    UUID speedUUID = UUID.nameUUIDFromBytes((playerUUID + roleId + "_speed").getBytes());
                    removeAttributeModifier(player, Attributes.MOVEMENT_SPEED, speedUUID);
                    
                    // 移防御力修饰符
                    UUID armorUUID = UUID.nameUUIDFromBytes((playerUUID + roleId + "_armor").getBytes());
                    removeAttributeModifier(player, Attributes.ARMOR, armorUUID);
                }
            }
        });
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