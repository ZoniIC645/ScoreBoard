package com.zoniic645.scoreboard.compat;

import net.minecraftforge.fml.common.Loader;
import com.zoniic645.scoreboard.compat.top.TOPCompatibility;

public class MainCompatHandler {
    public static void registerTOP() {
        if (Loader.isModLoaded("theoneprobe")) {
            TOPCompatibility.register();
        }
    }

}