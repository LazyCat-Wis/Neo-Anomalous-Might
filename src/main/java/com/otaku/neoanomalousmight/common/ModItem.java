package com.otaku.neoanomalousmight.common;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.otaku.neoanomalousmight.Neo_Anomalous_Might;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Neo_Anomalous_Might.MOD_ID);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}