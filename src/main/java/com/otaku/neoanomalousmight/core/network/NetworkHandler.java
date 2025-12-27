package com.otaku.neoanomalousmight.core.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.otaku.neoanomalousmight.Neo_Anomalous_Might.MOD_ID;

/**
 * 网络通信处理类
 * 管理客户端和服务器之间的通信通道和数据包
 */
public class NetworkHandler {
    
    // 协议版本号，当数据包结构改变时需要更新
    private static final String PROTOCOL_VERSION = "1.0";
    
    // 创建网络通道
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    
    /**
     * 注册所有网络数据包
     */
    public static void registerPackets() {
        int packetId = 0;
        
        // 注册角色选择数据包
        INSTANCE.registerMessage(
                packetId++,
                C2SChooseRolePacket.class,
                C2SChooseRolePacket::encode,
                C2SChooseRolePacket::decode,
                C2SChooseRolePacket::handle
        );
        
        // 注册其他数据包（如果有）
        // INSTANCE.registerMessage(...);
    }
}