package com.otaku.neoanomalousmight.registration;

import com.otaku.neoanomalousmight.Neo_Anomalous_Might;
import com.otaku.neoanomalousmight.element.ElementType;
import com.otaku.neoanomalousmight.role.Role;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

/**
 * 统一注册管理器
 * 使用正确的Forge API创建自定义注册表
 */
@Mod.EventBusSubscriber(modid = Neo_Anomalous_Might.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistration {
    // 定义注册表键
    public static final ResourceKey<net.minecraft.core.Registry<ElementType>> ELEMENT_TYPE_REGISTRY_KEY = 
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Neo_Anomalous_Might.MOD_ID, "element_types"));

    public static final ResourceKey<net.minecraft.core.Registry<Role>> ROLE_REGISTRY_KEY = 
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Neo_Anomalous_Might.MOD_ID, "roles"));

    // 注册器 - 用于注册具体的元素类型
    public static final DeferredRegister<ElementType> ELEMENT_TYPES = 
            DeferredRegister.create(ELEMENT_TYPE_REGISTRY_KEY, Neo_Anomalous_Might.MOD_ID);

    // 注册表实例的Supplier
    private static Supplier<IForgeRegistry<ElementType>> elementTypeRegistrySupplier;
    private static Supplier<IForgeRegistry<Role>> roleRegistrySupplier;

    /**
     * 初始化注册系统
     * @param modEventBus 模组事件总线
     */
    public static void init(IEventBus modEventBus) {
        // 注册元素和角色实例
        ELEMENT_TYPES.register(modEventBus);
    }

    /**
     * 处理自定义注册表创建事件
     */
    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) {
        // 创建元素类型注册表
        elementTypeRegistrySupplier = event.create(
                new RegistryBuilder<ElementType>()
                        .setName(ELEMENT_TYPE_REGISTRY_KEY.location())
                        .allowModification()
                        .disableSaving()
        );

        // 创建角色注册表
        roleRegistrySupplier = event.create(
                new RegistryBuilder<Role>()
                        .setName(ROLE_REGISTRY_KEY.location())
                        .allowModification()
                        .disableSaving()
        );
    }

    /**
     * 获取元素类型注册表实例
     */
    public static IForgeRegistry<ElementType> getElementTypeRegistry() {
        return elementTypeRegistrySupplier.get();
    }

    /**
     * 获取角色注册表实例
     */
    public static IForgeRegistry<Role> getRoleRegistry() {
        return roleRegistrySupplier.get();
    }
}
