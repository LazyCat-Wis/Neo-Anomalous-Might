package com.otaku.neoanomalousmight.command;

import com.mojang.brigadier.CommandDispatcher;
import com.otaku.neoanomalousmight.client.gui.RoleSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;                                                   
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.otaku.neoanomalousmight.Neo_Anomalous_Might.MOD_ID;

/**
 * 调试命令类
 * 用于直接打开角色选择界面
 */
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DebugRoleCommand {
    
    /**
     * 注册命令
     * @param event 命令注册事件
     */
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        
        // 注册debugrole命令
        dispatcher.register(Commands.literal("debugrole")
                .requires(source -> source.hasPermission(0)) // 所有玩家都可以使用
                .executes(context -> {
                    // 在客户端执行打开界面操作
                    if (context.getSource().isPlayer()) {
                        // 发送命令成功消息
                        context.getSource().sendSuccess(() -> Component.literal("打开角色选择界面..."), true);
                        return 1;
                    }
                    return 0;
                })
        );
    }
    
    /**
     * 客户端事件处理类
     */
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientHandler {
        
        /**
         * 注册客户端命令
         * @param event 命令注册事件
         */
        @SubscribeEvent
        public static void registerClientCommands(RegisterCommandsEvent event) {
            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
            
            // 注册客户端debugrole命令
            dispatcher.register(Commands.literal("debugrole")
                    .requires(source -> source.hasPermission(0))
                    .executes(context -> {
                        // 直接在客户端打开界面
                        Minecraft.getInstance().execute(() -> {
                            Minecraft.getInstance().setScreen(new RoleSelectionScreen());
                        });
                        return 1;
                    })
            );
        }
    }
}