package com.otaku.neoanomalousmight.element;

import com.otaku.neoanomalousmight.Neo_Anomalous_Might;
import com.otaku.neoanomalousmight.init.ModRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * 元素系统核心管理类
 * 负责注册和管理所有元素相关的内容
 */
public class Elements {

    public static final DeferredRegister<ElementType> ELEMENT_TYPES = DeferredRegister.create(
            ModRegistration.ELEMENT_TYPE_REGISTRY_KEY, Neo_Anomalous_Might.MOD_ID);
    public static void registerElements() {
        // 元素类型会在游戏启动时自动注册
    }
    
    // 注册所有元素类型
    public static final RegistryObject<ElementType> FIRE = registerElementType(ElementType.FIRE);
    public static final RegistryObject<ElementType> WATER = registerElementType(ElementType.WATER);
    public static final RegistryObject<ElementType> WIND = registerElementType(ElementType.WIND);
    public static final RegistryObject<ElementType> THUNDER = registerElementType(ElementType.THUNDER);
    public static final RegistryObject<ElementType> GRASS = registerElementType(ElementType.GRASS);
    public static final RegistryObject<ElementType> ICE = registerElementType(ElementType.ICE);
    public static final RegistryObject<ElementType> EARTH = registerElementType(ElementType.EARTH);
    public static final RegistryObject<ElementType> NONE = registerElementType(ElementType.NONE);
    
    /**
     * 注册元素类型
     * @param elementType 元素类型
     * @return 注册对象
     */
    private static RegistryObject<ElementType> registerElementType(ElementType elementType) {
        return ELEMENT_TYPES.register(elementType.name().toLowerCase(), () -> elementType);
    }
    
    /**
     * 获取元素类型的资源位置
     * @param elementType 元素类型
     * @return 资源位置
     */
    public static ResourceLocation getElementResourceLocation(ElementType elementType) {
        return ResourceLocation.fromNamespaceAndPath(Neo_Anomalous_Might.MOD_ID, elementType.name().toLowerCase());
    }
    
    /**
     * 获取元素类型的显示名称
     * @param elementType 元素类型
     * @return 显示名称
     */
    public static String getElementDisplayName(ElementType elementType) {
        return elementType.getChineseName();
    }
    
    /**
     * 获取元素类型的英文名称
     * @param elementType 元素类型
     * @return 英文名称
     */
    public static String getElementEnglishName(ElementType elementType) {
        return elementType.getEnglishName();
    }
}