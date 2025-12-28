package com.otaku.neoanomalousmight.capability.player;

import com.otaku.neoanomalousmight.role.Role;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public interface IPlayerRole {
    Role getRole();
    void setRole(Role role);
    ResourceLocation getRoleId();
    void setRoleId(ResourceLocation roleId);
    CompoundTag serializeNBT();
    void deserializeNBT(CompoundTag nbt);
    default boolean hasSelectedRole () {
        return getRoleId() != null;
    }
}