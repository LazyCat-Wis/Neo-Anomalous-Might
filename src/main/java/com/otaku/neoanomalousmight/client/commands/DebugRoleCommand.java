package com.otaku.neoanomalousmight.client.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.otaku.neoanomalousmight.client.gui.RoleSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.otaku.neoanomalousmight.Neo_Anomalous_Might.MOD_ID;

/**
 * 调试命令类，用于测试角色选择界面
 */
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DebugRoleCommand {

    /**
     * 注册客户端命令
     * @param event 命令注册事件
     */
    @SubscribeEvent
    public static void registerCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        
        // 注册打开角色选择界面的命令
        dispatcher.register(Commands.literal("debugrole")
            .executes(context -> {
                // 在客户端主线程中打开角色选择界面
                Minecraft.getInstance().execute(() -> {
                    Minecraft.getInstance().setScreen(new RoleSelectionScreen());
                });
                context.getSource().sendSuccess(() -> Component.literal("已打开角色选择界面"), true);
                return 1;
            })
        );
    }
}
