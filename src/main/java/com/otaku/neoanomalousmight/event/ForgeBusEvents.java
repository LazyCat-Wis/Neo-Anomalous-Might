package com.otaku.neoanomalousmight.event;


import com.otaku.neoanomalousmight.capability.player.IPlayerRole;
import com.otaku.neoanomalousmight.capability.player.PlayerRoleProvider;
import com.otaku.neoanomalousmight.client.gui.RoleSelectionScreen;
import com.otaku.neoanomalousmight.element.ElementalInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.common.util.LazyOptional;

import static com.otaku.neoanomalousmight.Neo_Anomalous_Might.MOD_ID;

/**
 * Forge事件处理类
 * 处理游戏中的各种事件，如玩家登录、攻击、伤害等
 */
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeBusEvents {

    /**
     * 玩家加入世界事件
     * 当玩家第一次进入世界时触发
     * @param event 实体加入世界事件
     */
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            System.out.println("[NeoAnomalousMight] Player joined world: " + player.getName().getString());
            System.out.println("[NeoAnomalousMight] Is client side: " + player.level().isClientSide());
            
            // 直接在客户端打开角色选择界面，不检查角色状态
            if (player.level().isClientSide()) {
                System.out.println("[NeoAnomalousMight] Opening role selection screen for player: " + player.getName().getString());
                // 延迟打开界面，确保游戏完全加载
                net.minecraft.client.Minecraft.getInstance().execute(() -> {
                    RoleSelectionScreen.open();
                });
            }
        }
    }

    /**
     * 玩家登录事件
     * 当玩家首次登录服务器时触发
     * @param event 玩家登录事件
     */
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        System.out.println("[NeoAnomalousMight] Player logged in: " + player.getName().getString());
        System.out.println("[NeoAnomalousMight] Is client side: " + player.level().isClientSide());
        // 检查玩家是否已经选择了角色
        LazyOptional<IPlayerRole> roleOptional = player.getCapability(PlayerRoleProvider.PLAYER_ROLE_CAPABILITY);
        System.out.println("[NeoAnomalousMight] Capability present (login): " + roleOptional.isPresent());
        
        roleOptional.ifPresent(role -> {
            System.out.println("[NeoAnomalousMight] Player has role (login): " + role.hasSelectedRole());
            if (!role.hasSelectedRole()) {
                // 如果是客户端，打开角色选择界面
                if (player.level().isClientSide()) {
                    System.out.println("[NeoAnomalousMight] Opening role selection screen on login for player: " + player.getName().getString());
                    // 延迟打开界面，确保游戏完全加载
                    net.minecraft.client.Minecraft.getInstance().execute(() -> {
                        RoleSelectionScreen.open();
                    });
                }
            }
        });
        
        if (!roleOptional.isPresent()) {
            System.out.println("[NeoAnomalousMight] WARNING: Player does not have role capability on login!");
        }
    }

    /**
     * 玩家克隆事件
     * 当玩家死亡或重生时触发
     * @param event 玩家克隆事件
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player originalPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        // 获取原玩家的角色能力
        originalPlayer.getCapability(PlayerRoleProvider.PLAYER_ROLE_CAPABILITY).ifPresent(oldRole -> {
            // 获取新玩家的角色能力
            newPlayer.getCapability(PlayerRoleProvider.PLAYER_ROLE_CAPABILITY).ifPresent(newRole -> {
                // 复制角色数据
                newRole.setRole(oldRole.getRole());
            });
        });
    }

    /**
     * 玩家攻击事件
     * 当玩家攻击实体时触发
     * @param event 实体攻击事件
     */
    @SubscribeEvent
    public static void onPlayerAttack(LivingAttackEvent event) {
        LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity ?
                (LivingEntity) event.getSource().getEntity() : null;
        LivingEntity target = event.getEntity();

        if (attacker != null && attacker instanceof Player player) {
            // 应用元素攻击效果
            player.getCapability(PlayerRoleProvider.PLAYER_ROLE_CAPABILITY).ifPresent(role -> {
                if (role.hasSelectedRole()) {
                    ElementalInteraction.applyElementalEffect(player, target, role.getRole().getElementType(), com.otaku.neoanomalousmight.element.ElementType.NONE);
                }
            });
        }
    }

    /**
     * 实体受到伤害事件
     * 当实体受到伤害时触发
     * @param event 实体伤害事件
     */
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        
        if (entity instanceof Player player) {
            // 获取玩家的角色能力
            player.getCapability(PlayerRoleProvider.PLAYER_ROLE_CAPABILITY).ifPresent(role -> {
                if (role.hasSelectedRole()) {
                    // 元素互动效果已在onPlayerAttack方法中通过applyElementalEffect实现
                }
            });
        }
    }

    /**
     * 客户端事件处理类
     * 处理客户端特定的事件
     */
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientEvents {

        /**
         * 客户端玩家Tick事件
         * 每游戏刻在客户端触发一次
         * @param event 客户端Tick事件
         */
        @SubscribeEvent
        public static void onClientPlayerTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                Player player = net.minecraft.client.Minecraft.getInstance().player;
                if (player != null) {
                    // 更新玩家的移动速度的代码已移除，避免与PlayerAttributeEventHandler冲突
                    // 移动速度现在由PlayerAttributeEventHandler类统一管理
                }
            }
        }

        /**
         * 键盘输入事件
         * 当玩家按下键盘按键时触发
         * @param event 键盘输入事件
         */
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            // 这里可以添加键盘快捷键处理逻辑
            // 例如，按某个键打开角色选择界面
        }
    }

    /**
     * 服务器事件处理类
     * 处理服务器特定的事件
     */
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
    public static class ServerEvents {

        /**
         * 服务器玩家Tick事件
         * 每游戏刻在服务器触发一次
         * @param event 服务器Tick事件
         */
        @SubscribeEvent
        public static void onServerPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                Player player = event.player;
                if (!player.level().isClientSide()) {
                }
            }
        }
    }
}










