package com.zoniic645.scoreboard.compat.crafttweaker;

import blusunrize.immersiveengineering.common.util.compat.crafttweaker.CraftTweakerHelper;
import com.zoniic645.scoreboard.score.FluidScore;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidStack;

public class ActionAddFluidScore implements IAction {
    private final float score;
    private final ILiquidStack input;

    public ActionAddFluidScore(ILiquidStack stack, float scoreIn) {
        this.score = scoreIn;
        this.input = stack;
    }

    @Override
    public void apply() {
        FluidScore.addScore(CraftTweakerHelper.toFluidStack(input).getFluid(), score);
    }

    @Override
    public String describe() {
        return "add fluid : " + input.getDisplayName() + "score : " + score;
    }
}
