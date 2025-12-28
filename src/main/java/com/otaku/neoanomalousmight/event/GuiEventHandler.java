package com.otaku.neoanomalousmight.event;

import com.otaku.neoanomalousmight.client.gui.AttributeGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.otaku.neoanomalousmight.Neo_Anomalous_Might.MOD_ID;

/**
 * GUI事件处理器
 * 处理游戏中的GUI相关事件，如在生存物品栏上方添加属性按钮
 */
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class GuiEventHandler {

    /**
     * 当GUI屏幕初始化完成后触发
     * @param event 屏幕初始化后事件
     */
    @SubscribeEvent
    public static void onScreenInitPost(ScreenEvent.Init.Post event) {
        // 检查当前屏幕是否是生存模式物品栏
        if (event.getScreen() instanceof InventoryScreen) {
            InventoryScreen inventoryScreen = (InventoryScreen) event.getScreen();
            
            // 添加属性按钮
            addAttributeButton(event, inventoryScreen);
        }
    }
    
    /**
     * 在生存物品栏上方添加属性按钮
     * @param event 屏幕初始化后事件
     * @param inventoryScreen 库存屏幕
     */
    private static void addAttributeButton(ScreenEvent.Init.Post event, InventoryScreen inventoryScreen) {
        // 计算按钮位置：在物品栏上方居中
        int screenWidth = event.getScreen().width;
        int buttonWidth = 20;
        int buttonHeight = 20;
        int buttonX = (screenWidth - buttonWidth) / 2;
        int buttonY = event.getScreen().height / 2 - 140;
        
        // 创建按钮
        Button attributeButton = Button.builder(
                Component.literal("⚔"), // 使用武器图标作为属性按钮
                button -> {
                    // 打开属性GUI
                    Minecraft.getInstance().setScreen(new AttributeGuiScreen());
                }
        ).pos(buttonX, buttonY).size(buttonWidth, buttonHeight)
         .build();
        
        // 将按钮添加到屏幕
        event.addListener(attributeButton);
    }
}