package com.zoniic645.scoreboard;

import com.feed_the_beast.ftblib.FTBLib;
import com.zoniic645.scoreboard.Proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(
        modid = ScoreBoard.MODID,
        name = ScoreBoard.NAME,
        version = ScoreBoard.VERSION,
        dependencies = FTBLib.THIS_DEP
)
public class ScoreBoard {
    public static final String MODID = "scoreboard";
    public static final String NAME = "scoreboard";
    public static final String VERSION = "0.0.0.0";

    @SidedProxy(clientSide = "com.zoniic645.scoreboard.Proxy.ClientProxy", serverSide = "com.zoniic645.scoreboard.Proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModContents.registerItem(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            ModContents.registerBlock(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModContents.reigsterModels();
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        ModContents.registerScore();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
