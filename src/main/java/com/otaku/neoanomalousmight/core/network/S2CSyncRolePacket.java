package com.otaku.neoanomalousmight.core.network;

import com.otaku.neoanomalousmight.capability.player.IPlayerRole;
import com.otaku.neoanomalousmight.capability.player.PlayerRoleProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

/**
 * 服务器到客户端的角色数据同步数据包
 * 用于将玩家的角色数据从服务器同步到客户端
 */
public class S2CSyncRolePacket {
    
    // 玩家的UUID
    private final CompoundTag playerRoleData;
    
    /**
     * 构造函数
     * @param roleCapability 玩家角色能力
     */
    public S2CSyncRolePacket(IPlayerRole roleCapability) {
        this.playerRoleData = roleCapability.serializeNBT();
    }
    
    /**
     * 构造函数
     * @param playerRoleData 玩家角色数据NBT
     */
    public S2CSyncRolePacket(CompoundTag playerRoleData) {
        this.playerRoleData = playerRoleData;
    }
    
    /**
     * 编码数据包
     * @param message 数据包对象
     * @param buffer 字节缓冲区
     */
    public static void encode(S2CSyncRolePacket message, FriendlyByteBuf buffer) {
        buffer.writeNbt(message.playerRoleData);
    }
    
    /**
     * 解码数据包
     * @param buffer 字节缓冲区
     * @return 解码后的数据包对象
     */
    public static S2CSyncRolePacket decode(FriendlyByteBuf buffer) {
        return new S2CSyncRolePacket(buffer.readNbt());
    }
    
    /**
     * 处理数据包
     * @param message 数据包对象
     * @param context 网络事件上下文
     */
    public static void handle(S2CSyncRolePacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            // 获取客户端玩家
            Player player = Minecraft.getInstance().player;
            if (player == null) return;
            
            // 加载角色数据
            player.getCapability(PlayerRoleProvider.PLAYER_ROLE_CAPABILITY).ifPresent(roleCapability -> {
                roleCapability.deserializeNBT(message.playerRoleData);
            });
        });
        
        // 完成数据包处理
        context.get().setPacketHandled(true);
    }
}