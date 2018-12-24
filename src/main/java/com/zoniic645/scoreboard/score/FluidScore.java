package com.zoniic645.scoreboard.score;

import net.minecraftforge.fluids.Fluid;

import java.util.HashMap;

public class FluidScore {
    private final static FluidScore SCORE = new FluidScore();

    public static FluidScore getInstance() {
        return SCORE;
    }

    private static HashMap<Fluid, Float> FluidBasedScore = new HashMap<>();

    public static void addScore(Fluid fluid, float score) {
        FluidBasedScore.put(fluid, score);
    }

    //점수 받아옴
    public static float getScore(Fluid fluid) {
        if (FluidBasedScore.get(fluid) != null)
            return FluidBasedScore.get(fluid);
        return 0;
    }

}
