package com.zoniic645.scoreboard;

import net.minecraftforge.fml.common.Loader;
import com.zoniic645.scoreboard.theoneprobecompatibility.TOPCompatibility;

public class MainCompatHandler {
    public static void registerTOP() {
        if (Loader.isModLoaded("theoneprobe")) {
            TOPCompatibility.register();
        }
    }

}