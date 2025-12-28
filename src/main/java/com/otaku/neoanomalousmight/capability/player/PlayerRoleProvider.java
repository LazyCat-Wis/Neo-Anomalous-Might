// File: src/main/java/com/otaku/neoanomalousmight/capability/player/PlayerRoleProvider.java
package com.otaku.neoanomalousmight.capability.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.otaku.neoanomalousmight.Neo_Anomalous_Might.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerRoleProvider implements ICapabilitySerializable<CompoundTag> {
    public static final ResourceLocation PLAYER_ROLE_CAP = ResourceLocation.fromNamespaceAndPath(MOD_ID, "player_role");
    public static final Capability<IPlayerRole> PLAYER_ROLE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    
    private final PlayerRole playerRole = new PlayerRole();
    private final LazyOptional<IPlayerRole> opt = LazyOptional.of(() -> playerRole);

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_ROLE_CAPABILITY.orEmpty(cap, opt);
    }

    @Override
    public CompoundTag serializeNBT() {
        return playerRole.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        playerRole.deserializeNBT(nbt);
    }
    
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(PLAYER_ROLE_CAP, new PlayerRoleProvider());
        }
    }
}