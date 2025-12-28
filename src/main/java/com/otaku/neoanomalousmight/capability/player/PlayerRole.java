// File: src/main/java/com/otaku/neoanomalousmight/capability/player/PlayerRole.java
package com.otaku.neoanomalousmight.capability.player;

import com.otaku.neoanomalousmight.Neo_Anomalous_Might;
import com.otaku.neoanomalousmight.role.Role;
import com.otaku.neoanomalousmight.role.ModRoles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

public class PlayerRole implements IPlayerRole, INBTSerializable<CompoundTag> { // *** 实现 INBTSerializable<CompoundTag> ***

    // *** 只存储 ResourceLocation 类型的 roleId ***
    private ResourceLocation roleId = null;

    @Override
    public ResourceLocation getRoleId() {
        return this.roleId;
    }

    @Override
    public void setRoleId(ResourceLocation roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean hasSelectedRole() {
        return this.roleId != null;
    }

    // 根据 roleId 获取 Role 对象
    @Override
    public @Nullable Role getRole() {
        if (this.roleId == null) {
            return null;
        }
        // 使用 ModRoles 类获取已注册的角色
        return ModRoles.getAllRoles().stream()
                .filter(role -> role.getRegistryName().equals(this.roleId))
                .findFirst()
                .orElse(null);
    }
    
    // 设置角色
    @Override
    public void setRole(Role role) {
        if (role != null) {
            this.roleId = role.getRegistryName();
        } else {
            this.roleId = null;
        }
    }

    // *** 实现 INBTSerializable 接口的方法 ***
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        if (this.roleId != null) {
            // *** 关键：将 ResourceLocation 写入 NBT ***
            nbt.putString("RoleId", this.roleId.toString());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        // *** 关键：从 NBT 读取 ResourceLocation ***
        if (nbt.contains("RoleId")) {
            String roleIdStr = nbt.getString("RoleId");
            if (!roleIdStr.isEmpty()) {
                this.roleId = ResourceLocation.tryParse(roleIdStr); // 使用 tryParse 更安全
                if (this.roleId == null) {
                    Neo_Anomalous_Might.LOGGER.error("Failed to parse RoleId from NBT string: {}", roleIdStr);
                }
            } else {
                this.roleId = null; // 字符串为空，视作未选择
            }
        } else {
            this.roleId = null; // NBT 中没有该键，视作未选择
        }
    }
    // *** INBTSerializable 实现结束 ***
}