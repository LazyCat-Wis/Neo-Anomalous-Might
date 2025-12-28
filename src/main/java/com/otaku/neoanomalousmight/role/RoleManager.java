package com.otaku.neoanomalousmight.role;

import com.otaku.neoanomalousmight.capability.player.IPlayerRole;
import com.otaku.neoanomalousmight.capability.player.PlayerRoleProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

/**
 * 角色管理器接口
 * 提供统一的角色系统访问入口，连接到新的capability系统
 * 所有角色相关的方法都应该通过此接口进行访问
 */
public class RoleManager {
    
    /**
     * 获取玩家的角色能力
     * @param player 玩家对象
     * @return 角色能力的LazyOptional
     */
    public static LazyOptional<IPlayerRole> getPlayerRole(Player player) {
        return player.getCapability(PlayerRoleProvider.PLAYER_ROLE_CAPABILITY);
    }
    
    /**
     * 安全地获取玩家的角色能力
     * 如果玩家没有角色能力，则返回一个默认实现
     * @param player 玩家对象
     * @return 角色能力实例
     */
    public static IPlayerRole getPlayerRoleSafe(Player player) {
        return getPlayerRole(player).orElseThrow(() -> new IllegalArgumentException("Player has no role capability"));
    }
    
    /**
     * 检查玩家是否已经选择了角色
     * @param player 玩家对象
     * @return 如果玩家已经选择角色则返回true，否则返回false
     */
    public static boolean hasSelectedRole(Player player) {
        return getPlayerRole(player).map(IPlayerRole::hasSelectedRole).orElse(false);
    }
    
    /**
     * 获取玩家选择的角色
     * @param player 玩家对象
     * @return 玩家选择的角色，如果没有选择则返回null
     */
    public static Role getSelectedRole(Player player) {
        return getPlayerRole(player).map(IPlayerRole::getRole).orElse(null);
    }
    
    /**
     * 设置玩家选择的角色
     * @param player 玩家对象
     * @param role 要设置的角色
     */
    public static void setSelectedRole(Player player, Role role) {
        getPlayerRole(player).ifPresent(cap -> cap.setRole(role));
    }
}