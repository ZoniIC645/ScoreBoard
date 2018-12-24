package com.zoniic645.scoreboard.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.ScoreBoard")
public class CraftTweakerCompatibility {
    @ZenMethod
    public static void addItemScore(IItemStack input, int score) {
        CraftTweakerAPI.apply(new ActionAddItemScore(score, input));
    }

    @ZenMethod
    public static void addFluidScore(ILiquidStack input, float score) {
        CraftTweakerAPI.apply(new ActionAddFluidScore(input, score));
    }
}