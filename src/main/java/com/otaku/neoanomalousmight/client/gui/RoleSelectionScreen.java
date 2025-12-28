package com.otaku.neoanomalousmight.client.gui;

import com.otaku.neoanomalousmight.core.network.C2SChooseRolePacket;
import com.otaku.neoanomalousmight.role.ModRoles;
import com.otaku.neoanomalousmight.role.Role;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


import static com.otaku.neoanomalousmight.core.network.NetworkHandler.INSTANCE;

/**
 * 角色选择界面
 * 允许玩家选择游戏角色
 */
public class RoleSelectionScreen extends Screen {

    // 界面尺寸 - 增大GUI面积
    // 这些常量现在由自动调整大小逻辑替代
    // private static final int WIDTH = 384;  // 从256增大到384
    // private static final int HEIGHT = 288; // 从192增大到288

    // 角色按钮的位置和尺寸
    private static final int BUTTON_WIDTH = 100;  // 从120减小到100
    private static final int BUTTON_HEIGHT = 20;  // 从25减小到20
    // private static final int BUTTON_SPACING = 15;  // 从10增大到15 - 不再使用

    // 选中的角色索引
    private int selectedRoleIndex = 0;
    // 所有可选择的角色列表
    private java.util.List<Role> availableRoles;
    // 保存创建的角色按钮引用，用于高亮显示
    private java.util.List<Button> roleButtons;
    
    /**
     * 构造函数
     */
    public RoleSelectionScreen() {
        super(Component.translatable("screen.neo_anomalous_might.role_selection.title"));
        availableRoles = ModRoles.getAllRoles();
        roleButtons = new java.util.ArrayList<>();
    }

    /**
     * 初始化界面组件
     */
    @Override
    protected void init() {
        super.init();
        
        // 清空按钮列表
        roleButtons.clear();
        
        // 根据窗口大小自动调整GUI大小
        int windowWidth = this.width;
        int windowHeight = this.height;
        
        // 设置GUI大小为窗口的70%，但不超过最大尺寸
        int guiWidth = Math.min((int)(windowWidth * 0.7), 1000); // 增大最大宽度
        int guiHeight = Math.min((int)(windowHeight * 0.7), 800); // 增大最大高度
        
        // 计算界面的中心位置
        int centerX = (this.width - guiWidth) / 2;
        int centerY = (this.height - guiHeight) / 2;
        
        // 创建角色选择按钮（左边列表）
        int listWidth = BUTTON_WIDTH;
        // int listHeight = guiHeight - 80; // 不再使用
        int listX = centerX + 20;
        int listY = centerY + 40;
        
        // 创建角色选择按钮
        for (int i = 0; i < availableRoles.size(); i++) {
            Role role = availableRoles.get(i);
            int buttonY = listY + i * (BUTTON_HEIGHT + 5);
            
            // 为每个按钮创建一个最终的索引变量
            final int roleIndex = i;
            
            Button roleButton = Button.builder(
                    Component.literal(role.getName()),
                    button -> {
                        selectedRoleIndex = roleIndex;
                    }
            ).pos(listX, buttonY).size(listWidth, BUTTON_HEIGHT).build();
            
            this.addRenderableWidget(roleButton);
            roleButtons.add(roleButton); // 保存按钮引用
        }
        
        // 创建确认按钮
        Button confirmButton = Button.builder(
                Component.translatable("gui.done"),
                button -> {
                    if (selectedRoleIndex < availableRoles.size()) {
                        this.selectRole(availableRoles.get(selectedRoleIndex));
                    }
                }
        ).pos(centerX + (guiWidth - BUTTON_WIDTH) / 2, centerY + guiHeight - 35).size(BUTTON_WIDTH, BUTTON_HEIGHT).build();
        
        this.addRenderableWidget(confirmButton);
        
        // 创建关闭按钮
        Button closeButton = Button.builder(
                Component.translatable("gui.cancel"),
                button -> this.onClose()
        ).pos(centerX + guiWidth - BUTTON_WIDTH - 20, centerY + guiHeight - 35).size(BUTTON_WIDTH, BUTTON_HEIGHT).build();
        
        this.addRenderableWidget(closeButton);
    }

    /**
     * 选择角色的处理逻辑
     * @param role 选择的角色
     */
    private void selectRole(Role role) {
        // 发送角色选择数据包到服务器
        INSTANCE.sendToServer(new C2SChooseRolePacket(role));

        // 关闭角色选择界面
        this.onClose();
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
        
        // 根据窗口大小自动调整GUI大小
        int windowWidth = this.width;
        int windowHeight = this.height;
        
        // 设置GUI大小为窗口的70%，但不超过最大尺寸
        int guiWidth = Math.min((int)(windowWidth * 0.7), 1000); // 增大最大宽度
        int guiHeight = Math.min((int)(windowHeight * 0.7), 800); // 增大最大高度
        
        // 计算界面的中心位置
        int centerX = (this.width - guiWidth) / 2;
        int centerY = (this.height - guiHeight) / 2;
        
        // 直接绘制背景，不使用外部纹理文件
        guiGraphics.fill(centerX, centerY, centerX + guiWidth, centerY + guiHeight, 0x80000000); // 半透明黑色背景
        guiGraphics.fill(centerX + 5, centerY + 5, centerX + guiWidth - 5, centerY + guiHeight - 5, 0xFF222222); // 深灰色内部
        
        // 渲染标题
        guiGraphics.drawCenteredString(this.font, this.title.getString(), centerX + guiWidth / 2, centerY + 20, 0xFFFFFF);
        
        // 渲染选中角色的详细属性（右边部分）
        if (selectedRoleIndex < availableRoles.size()) {
            Role selectedRole = availableRoles.get(selectedRoleIndex);
            
            int detailX = centerX + 150; // 向左移动，给角色列表更多空间
            int detailY = centerY + 40;
            int detailWidth = guiWidth - (detailX - centerX) - 20; // 可用宽度
            
            // 渲染角色名称
            guiGraphics.drawString(this.font, Component.literal("角色名称: " + selectedRole.getName()), detailX, detailY, 0xFFFFFF);
            detailY += 20;
            
            // 渲染角色元素类型
            guiGraphics.drawString(this.font, "元素: " + selectedRole.getElementType().getChineseName(), detailX, detailY, 0xFFFFFF);
            detailY += 20;
            
            // 渲染角色描述（支持换行）
            guiGraphics.drawString(this.font, "描述: ", detailX, detailY, 0xFFFFFF);
            detailY += 5;
            
            // 使用现代的font.split()方法实现自动换行
            String descriptionText = selectedRole.getDescription();
            java.util.List<net.minecraft.util.FormattedCharSequence> wrappedDescription = this.font.split(
                    net.minecraft.network.chat.Component.literal(descriptionText),
                    detailWidth - 10
            );
            
            // 逐行渲染换行后的文本
            for (net.minecraft.util.FormattedCharSequence line : wrappedDescription) {
                guiGraphics.drawString(this.font, line, detailX + 5, detailY, 0xFFFFFF);
                detailY += this.font.lineHeight; // 使用字体的实际行高
            }
            
            detailY += 10;
            
            // 渲染角色基础属性
            guiGraphics.drawString(this.font, "基础属性:", detailX, detailY, 0xFFFFFF);
            detailY += 20;
            
            // 计算每行文本的最大宽度（确保在GUI边界内）
            int maxLineWidth = detailWidth - 20;
            
            // 使用现代的方式渲染属性，确保换行
            // 直接渲染，不需要lambda表达式和数组包装
            
            // 生命属性
            String healthText = "生命: " + selectedRole.getBaseHealth();
            java.util.List<net.minecraft.util.FormattedCharSequence> healthLines = this.font.split(
                    net.minecraft.network.chat.Component.literal(healthText),
                    maxLineWidth
            );
            for (net.minecraft.util.FormattedCharSequence line : healthLines) {
                guiGraphics.drawString(this.font, line, detailX + 20, detailY, 0xFFFFFF);
                detailY += this.font.lineHeight;
            }
            
            // 攻击属性
            String attackText = "攻击: " + selectedRole.getBaseAttack();
            java.util.List<net.minecraft.util.FormattedCharSequence> attackLines = this.font.split(
                    net.minecraft.network.chat.Component.literal(attackText),
                    maxLineWidth
            );
            for (net.minecraft.util.FormattedCharSequence line : attackLines) {
                guiGraphics.drawString(this.font, line, detailX + 20, detailY, 0xFFFFFF);
                detailY += this.font.lineHeight;
            }
            
            // 防御属性
            String defenseText = "防御: " + selectedRole.getBaseDefense();
            java.util.List<net.minecraft.util.FormattedCharSequence> defenseLines = this.font.split(
                    net.minecraft.network.chat.Component.literal(defenseText),
                    maxLineWidth
            );
            for (net.minecraft.util.FormattedCharSequence line : defenseLines) {
                guiGraphics.drawString(this.font, line, detailX + 20, detailY, 0xFFFFFF);
                detailY += this.font.lineHeight;
            }
            
            // 法力属性
            String manaText = "法力: " + selectedRole.getBaseMana();
            java.util.List<net.minecraft.util.FormattedCharSequence> manaLines = this.font.split(
                    net.minecraft.network.chat.Component.literal(manaText),
                    maxLineWidth
            );
            for (net.minecraft.util.FormattedCharSequence line : manaLines) {
                guiGraphics.drawString(this.font, line, detailX + 20, detailY, 0xFFFFFF);
                detailY += this.font.lineHeight;
            }
            
            // 暴击几率
            String critChanceText = "暴击几率: " + String.format("%.1f", selectedRole.getBaseCritChance() * 100) + "%";
            java.util.List<net.minecraft.util.FormattedCharSequence> critChanceLines = this.font.split(
                    net.minecraft.network.chat.Component.literal(critChanceText),
                    maxLineWidth
            );
            for (net.minecraft.util.FormattedCharSequence line : critChanceLines) {
                guiGraphics.drawString(this.font, line, detailX + 20, detailY, 0xFFFFFF);
                detailY += this.font.lineHeight;
            }
            
            // 暴击伤害
            String critDamageText = "暴击伤害: " + String.format("%.1f", selectedRole.getBaseCritDamage() * 100) + "%";
            java.util.List<net.minecraft.util.FormattedCharSequence> critDamageLines = this.font.split(
                    net.minecraft.network.chat.Component.literal(critDamageText),
                    maxLineWidth
            );
            for (net.minecraft.util.FormattedCharSequence line : critDamageLines) {
                guiGraphics.drawString(this.font, line, detailX + 20, detailY, 0xFFFFFF);
                detailY += this.font.lineHeight;
            }
            
            // 闪避几率
            String evasionText = "闪避几率: " + String.format("%.1f", selectedRole.getBaseEvasion() * 100) + "%";
            java.util.List<net.minecraft.util.FormattedCharSequence> evasionLines = this.font.split(
                    net.minecraft.network.chat.Component.literal(evasionText),
                    maxLineWidth
            );
            for (net.minecraft.util.FormattedCharSequence line : evasionLines) {
                guiGraphics.drawString(this.font, line, detailX + 20, detailY, 0xFFFFFF);
                detailY += this.font.lineHeight;
            }
            
            // 移动速度
            String speedText = "移动速度: " + selectedRole.getBaseMovementSpeed();
            java.util.List<net.minecraft.util.FormattedCharSequence> speedLines = this.font.split(
                    net.minecraft.network.chat.Component.literal(speedText),
                    maxLineWidth
            );
            for (net.minecraft.util.FormattedCharSequence line : speedLines) {
                guiGraphics.drawString(this.font, line, detailX + 20, detailY, 0xFFFFFF);
                detailY += this.font.lineHeight;
            }
        }
        
        // 高亮显示选中的角色按钮
        if (selectedRoleIndex < roleButtons.size()) {
            Button selectedButton = roleButtons.get(selectedRoleIndex);
            int buttonX = selectedButton.getX();
            int buttonY = selectedButton.getY();
            int buttonWidth = selectedButton.getWidth();
            int buttonHeight = selectedButton.getHeight();
            
            // 绘制白色渐变高亮效果
            for (int i = 0; i < buttonHeight; i++) {
                float alpha = 0.1f + (float)i / buttonHeight * 0.2f;
                int color = (int)(alpha * 255) << 24 | 0xFFFFFF;
                guiGraphics.fill(buttonX, buttonY + i, buttonX + buttonWidth, buttonY + i + 1, color);
            }
        }
    }

    /**
     * 判断是否渲染玩家库存
     * @return 总是返回false，不在角色选择界面显示库存
     */
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    /**
     * 打开角色选择界面
     */
    public static void open() {
        Minecraft.getInstance().setScreen(new RoleSelectionScreen());
    }
}