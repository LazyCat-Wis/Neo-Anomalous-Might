package com.otaku.neoanomalousmight.client.gui;

import com.otaku.neoanomalousmight.capability.player.IPlayerRole;
import com.otaku.neoanomalousmight.capability.player.PlayerRoleProvider;
import com.otaku.neoanomalousmight.role.Role;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

/**
 * 属性GUI界面
 * 显示玩家的各种属性信息，使用原版BUFF UIGUI设计风格
 */
public class AttributeGuiScreen extends Screen {

    // 界面尺寸
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;
    
    // 按钮尺寸
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 20;
    
    private Player player;
    private Role playerRole;
    
    /**
     * 构造函数
     */
    public AttributeGuiScreen() {
        super(Component.translatable("screen.neo_anomalous_might.attribute.title"));
        this.player = Minecraft.getInstance().player;
        
        // 获取玩家角色
        if (player != null) {
            LazyOptional<IPlayerRole> roleOptional = player.getCapability(PlayerRoleProvider.PLAYER_ROLE_CAPABILITY);
            roleOptional.ifPresent(iPlayerRole -> {
                this.playerRole = iPlayerRole.getRole();
            });
        }
    }
    
    /**
     * 初始化界面组件
     */
    @Override
    protected void init() {
        super.init();
        
        // 计算界面的中心位置
        int centerX = (this.width - WIDTH) / 2;
        int centerY = (this.height - HEIGHT) / 2;
        
        // 创建关闭按钮
        Button closeButton = Button.builder(
                Component.translatable("gui.close"),
                button -> this.onClose()
        ).pos(centerX + WIDTH - BUTTON_WIDTH - 10, centerY + HEIGHT - BUTTON_HEIGHT - 10)
         .size(BUTTON_WIDTH, BUTTON_HEIGHT)
         .build();
        
        this.addRenderableWidget(closeButton);
    }
    
    /**
     * 渲染界面
     * @param guiGraphics GUI图形对象
     * @param mouseX 鼠标X坐标
     * @param mouseY 鼠标Y坐标
     * @param delta 增量时间
     */
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        // 渲染背景
        super.render(guiGraphics, mouseX, mouseY, delta);
        
        // 计算界面的中心位置
        int centerX = (this.width - WIDTH) / 2;
        int centerY = (this.height - HEIGHT) / 2;
        
        // 使用原版BUFF UIGUI设计风格渲染背景
        // 半透明黑色背景
        guiGraphics.fill(centerX, centerY, centerX + WIDTH, centerY + HEIGHT, 0x80000000);
        
        // 绘制边框
        guiGraphics.fill(centerX, centerY, centerX + WIDTH, centerY + 2, 0xFFAAAAAA); // 上边框
        guiGraphics.fill(centerX, centerY + HEIGHT - 2, centerX + WIDTH, centerY + HEIGHT, 0xFFAAAAAA); // 下边框
        guiGraphics.fill(centerX, centerY, centerX + 2, centerY + HEIGHT, 0xFFAAAAAA); // 左边框
        guiGraphics.fill(centerX + WIDTH - 2, centerY, centerX + WIDTH, centerY + HEIGHT, 0xFFAAAAAA); // 右边框
        
        // 渲染标题
        guiGraphics.drawCenteredString(this.font, this.title.getString(), centerX + WIDTH / 2, centerY + 10, 0xFFFFFF);
        
        // 渲染属性内容
        if (playerRole != null) {
            renderAttributes(guiGraphics, centerX, centerY);
        } else {
            guiGraphics.drawCenteredString(this.font, Component.translatable("screen.neo_anomalous_might.attribute.no_role"), 
                    centerX + WIDTH / 2, centerY + 50, 0xFFAAAAAA);
        }
    }
    
    /**
     * 渲染玩家属性
     * @param guiGraphics GUI图形对象
     * @param x 界面X坐标
     * @param y 界面Y坐标
     */
    private void renderAttributes(GuiGraphics guiGraphics, int x, int y) {
        int offsetX = x + 20;
        int offsetY = y + 40;
        int lineHeight = 20;
        
        // 渲染角色名称
        guiGraphics.drawString(this.font, Component.literal("角色: " + playerRole.getName()), offsetX, offsetY, 0xFFFFFF);
        offsetY += lineHeight;
        
        // 渲染生命属性
        guiGraphics.drawString(this.font, Component.literal("生命: " + (int)player.getMaxHealth() + "/" + (int)player.getHealth()), offsetX, offsetY, 0xFFFFFF);
        offsetY += lineHeight;
        
        // 渲染攻击属性
        guiGraphics.drawString(this.font, Component.literal("攻击: " + playerRole.getBaseAttack()), offsetX, offsetY, 0xFFFFFF);
        offsetY += lineHeight;
        
        // 渲染防御属性
        guiGraphics.drawString(this.font, Component.literal("防御: " + playerRole.getBaseDefense()), offsetX, offsetY, 0xFFFFFF);
        offsetY += lineHeight;
        
        // 渲染法力属性
        guiGraphics.drawString(this.font, Component.literal("法力: " + playerRole.getBaseMana()), offsetX, offsetY, 0xFFFFFF);
        offsetY += lineHeight;
        
        // 渲染暴击几率
        guiGraphics.drawString(this.font, Component.literal("暴击几率: " + String.format("%.1f", playerRole.getBaseCritChance() * 100) + "%"), offsetX, offsetY, 0xFFFFFF);
        offsetY += lineHeight;
        
        // 渲染暴击伤害
        guiGraphics.drawString(this.font, Component.literal("暴击伤害: " + String.format("%.1f", playerRole.getBaseCritDamage() * 100) + "%"), offsetX, offsetY, 0xFFFFFF);
        offsetY += lineHeight;
        
        // 渲染闪避几率
        guiGraphics.drawString(this.font, Component.literal("闪避几率: " + String.format("%.1f", playerRole.getBaseEvasion() * 100) + "%"), offsetX, offsetY, 0xFFFFFF);
        offsetY += lineHeight;
        
        // 渲染移动速度
        guiGraphics.drawString(this.font, Component.literal("移动速度: " + playerRole.getBaseMovementSpeed()), offsetX, offsetY, 0xFFFFFF);
    }
    
    /**
     * 判断是否渲染玩家库存
     * @return 总是返回false，不在属性界面显示库存
     */
    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
    /**
     * 打开属性GUI界面
     */
    public static void open() {
        Minecraft.getInstance().setScreen(new AttributeGuiScreen());
    }
}