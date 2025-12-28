package com.otaku.neoanomalousmight.core.network;


import com.otaku.neoanomalousmight.capability.player.PlayerRoleProvider;
import com.otaku.neoanomalousmight.role.ModRoles;
import com.otaku.neoanomalousmight.role.Role;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * 客户端到服务器的角色选择数据包
 * 用于玩家选择角色并通知服务器
 */
public class C2SChooseRolePacket {
    
    // 角色的资源位置
    private final ResourceLocation roleId;
    
    /**
     * 构造函数
     * @param role 选择的角色
     */
    public C2SChooseRolePacket(Role role) {
        this.roleId = role.getRegistryName();
    }
    
    /**
     * 构造函数
     * @param roleId 角色的资源位置
     */
    public C2SChooseRolePacket(ResourceLocation roleId) {
        this.roleId = roleId;
    }
    
    /**
     * 编码数据包
     * @param message 数据包对象
     * @param buffer 字节缓冲区
     */
    public static void encode(C2SChooseRolePacket message, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(message.roleId);
    }
    
    /**
     * 解码数据包
     * @param buffer 字节缓冲区
     * @return 解码后的数据包对象
     */
    public static C2SChooseRolePacket decode(FriendlyByteBuf buffer) {
        return new C2SChooseRolePacket(buffer.readResourceLocation());
    }
    
    /**
     * 处理数据包
     * @param message 数据包对象
     * @param context 网络事件上下文
     */
    public static void handle(C2SChooseRolePacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            // 获取服务器玩家
            ServerPlayer player = context.get().getSender();
            if (player == null) return;
            
            // 获取角色
            Role role = ModRoles.getRole(message.roleId);
            if (role == null) {
                // 如果角色不存在，记录错误
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("角色不存在！"));
                return;
            }
            
            // 设置玩家角色
            player.getCapability(PlayerRoleProvider.PLAYER_ROLE_CAPABILITY).ifPresent(roleCapability -> {
                roleCapability.setRole(role);
                
                // 通知玩家角色选择成功
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("你选择了角色: " + role.getName()));
                
                // 立即应用角色属性到玩家实体
                // 应用角色属性，直接调用静态方法
                com.otaku.neoanomalousmight.core.event.PlayerAttributeEventHandler.applyRoleAttributes(player);
            });
        });
        
        // 完成数据包处理
        context.get().setPacketHandled(true);
    }
}