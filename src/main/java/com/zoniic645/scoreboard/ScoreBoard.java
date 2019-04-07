package com.zoniic645.scoreboard;

import org.apache.logging.log4j.Logger;

import com.feed_the_beast.ftblib.FTBLib;
import com.zoniic645.scoreboard.compat.MainCompatHandler;
import com.zoniic645.scoreboard.proxy.CommonProxy;
import com.zoniic645.scoreboard.score.upload.ScoreUploader;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
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
    public static final String VERSION = "1.12.0.1";

    @Mod.Instance
    public static ScoreBoard instance;

    public static Logger logger;

    @SidedProxy(clientSide = "com.zoniic645.scoreboard.proxy.ClientProxy", serverSide = "com.zoniic645.scoreboard.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModContents.registerItem(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerBlock(RegistryEvent.Register<Block> event) {
            ModContents.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModContents.reigsterModels();
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        MainCompatHandler.registerTOP();
        //TaskSchedular.init();
        ScoreUploader.init();
    }
    
    @Mod.EventHandler
    public void onStop(FMLServerStoppedEvent event) {
    	ScoreUploader.update();
    }
}
