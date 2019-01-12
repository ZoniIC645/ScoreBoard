package com.zoniic645.scoreboard.compat.crafttweaker;

import blusunrize.immersiveengineering.common.util.compat.crafttweaker.CraftTweakerHelper;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.ScoreBoard")
public class CraftTweakerCompatibility {
	
    @ZenMethod
    public static void addItemScore(float quadraticTerm, float linearTerm, float constantTerm, IItemStack input, int score) {
        CraftTweakerAPI.apply(new ActionScore<ItemStack>(quadraticTerm, linearTerm, constantTerm, new ItemStack(CraftTweakerMC.getItemStack(input).getItem(), CraftTweakerMC.getItemStack(input).getCount(), CraftTweakerMC.getItemStack(input).getMetadata()), score));
    }

    @ZenMethod
    public static void addFluidScore(float quadraticTerm, float linearTerm, float constantTerm, ILiquidStack input, int score) {
        CraftTweakerAPI.apply(new ActionScore<Fluid>(quadraticTerm, linearTerm, constantTerm, CraftTweakerHelper.toFluidStack(input).getFluid(), score));
    }
}