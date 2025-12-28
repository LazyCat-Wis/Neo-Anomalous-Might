package com.otaku.neoanomalousmight;

import com.mojang.logging.LogUtils;
import com.otaku.neoanomalousmight.common.Config;
import com.otaku.neoanomalousmight.network.NetworkHandler;
import com.otaku.neoanomalousmight.element.Elements;
import com.otaku.neoanomalousmight.init.ModRegistration;
import com.otaku.neoanomalousmight.role.ModRoles;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Neo_Anomalous_Might.MOD_ID)
public class Neo_Anomalous_Might
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "neo_anomalous_might";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    
    // 按键映射：打开属性面板
    public static KeyMapping openAttributePanelKey;

    public Neo_Anomalous_Might()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);
        
        // Initialize the registration system
        ModRegistration.init(modEventBus);
        
        // Register the roles
        ModRoles.register(modEventBus);
        
        // Register the element system
        Elements.register(modEventBus);

        // Register the roles
        ModRoles.registerRoles();

        // Register to the Forge event bus
        MinecraftForge.EVENT_BUS.register(this);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        FMLJavaModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info("{}{}", Config.magicNumberIntroduction, Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
        
        // Register network packets
        NetworkHandler.registerPackets();
        LOGGER.info("Network packets registered");
        
        // Log the number of registered roles
        LOGGER.info("Registered roles: {}", ModRoles.getAllRoles().size());
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber and let the Event Bus discover methods to call
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}