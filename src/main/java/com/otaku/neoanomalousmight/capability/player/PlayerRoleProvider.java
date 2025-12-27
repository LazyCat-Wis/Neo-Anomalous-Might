package com.otaku.neoanomalousmight.capability.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.otaku.neoanomalousmight.Neo_Anomalous_Might.MOD_ID;

/**
 * 角色能力提供者类
 * 用于将角色能力附加到玩家实体上
 */
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerRoleProvider {
    
    // 创建角色能力的令牌
    public static final Capability<IPlayerRole> PLAYER_ROLE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    
    /**
     * 为玩家实体附加角色能力
     * @param event 附加能力事件
     */
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<LivingEntity> event) {
        if (event.getObject() instanceof Player) {
            System.out.println("[NeoAnomalousMight] Attaching role capability to player: " + event.getObject().getName().getString());
            // 创建角色能力提供者
            PlayerRoleImpl roleImpl = new PlayerRoleImpl();
            System.out.println("[NeoAnomalousMight] Created PlayerRoleImpl instance");
            
            // 附加能力
            event.addCapability(
                    net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(MOD_ID, "player_role"),
                    new ICapabilityProvider() {
                        @Nonnull
                        @Override
                        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                            System.out.println("[NeoAnomalousMight] getCapability called for cap: " + cap.getName());
                            System.out.println("[NeoAnomalousMight] PLAYER_ROLE_CAPABILITY name: " + PLAYER_ROLE_CAPABILITY.getName());
                            System.out.println("[NeoAnomalousMight] Capability match: " + PLAYER_ROLE_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> roleImpl)).isPresent());
                            return PLAYER_ROLE_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> roleImpl));
                        }
                    }
            );
            System.out.println("[NeoAnomalousMight] Role capability attached successfully");
        }
    }
    
    /**
     * MOD总线事件处理类
     * 用于处理需要在MOD总线上触发的事件
     */
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        /**
         * 注册角色能力
         * @param event 注册能力事件
         */
        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            System.out.println("[NeoAnomalousMight] Registering role capability");
            event.register(IPlayerRole.class);
            System.out.println("[NeoAnomalousMight] Role capability registered successfully");
        }
    }
    
    /**
     * 玩家死亡时复制角色能力
     * @param event 玩家克隆事件
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player originalPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        
        // 获取原玩家的角色能力
        originalPlayer.getCapability(PLAYER_ROLE_CAPABILITY).ifPresent(oldRole -> {
            // 获取新玩家的角色能力
            newPlayer.getCapability(PLAYER_ROLE_CAPABILITY).ifPresent(newRole -> {
                // 复制角色数据
                newRole.setSelectedRole(oldRole.getSelectedRole());
                newRole.setLevel(oldRole.getLevel());
                newRole.setExperience(oldRole.getExperience());
            });
        });
    }
    
    /**
     * 玩家进入世界时同步角色能力
     * @param event 玩家加入世界事件
     */
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        // 这个事件可以用于在玩家登录时同步角色数据到客户端
        Player player = event.getEntity();
        player.getCapability(PLAYER_ROLE_CAPABILITY).ifPresent(role -> {
            // 这里会在后续实现网络通信时添加同步逻辑
        });
    }
    
    /**
     * 角色能力的内部实现类
     * 继承PlayerRole并实现INBTSerializable接口
     */
    private static class PlayerRoleImpl extends PlayerRole implements INBTSerializable<CompoundTag> {
        
        @Override
        public CompoundTag serializeNBT() {
            return saveNBTData();
        }
        
        @Override
        public void deserializeNBT(CompoundTag nbt) {
            loadNBTData(nbt);
        }
    }
    
    /**
     * 从玩家实体获取角色能力
     * @param player 玩家实体
     * @return 角色能力的LazyOptional
     */
     public static LazyOptional<IPlayerRole> getPlayerRole(Player player) {
         return player.getCapability(PLAYER_ROLE_CAPABILITY);
     }
}