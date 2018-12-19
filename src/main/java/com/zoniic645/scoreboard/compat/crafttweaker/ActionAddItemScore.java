package com.zoniic645.scoreboard.compat.crafttweaker;

import com.zoniic645.scoreboard.ItemScore;
import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;

public class ActionAddItemScore implements IAction {
    private final int score;
    private final IItemStack input;

    public ActionAddItemScore(int scoreIn,IItemStack stack){
        this.score = scoreIn;
        this.input = stack;
    }

    @Override
    public void apply() {
        ItemScore.addScore(new ItemStack(CraftTweakerMC.getItemStack(input).getItem(), CraftTweakerMC.getItemStack(input).getCount(), CraftTweakerMC.getItemStack(input).getMetadata()),score);
    }

    @Override
    public String describe() {
        return "add item :" + CraftTweakerMC.getItemStack(input).getItem().getUnlocalizedName() +"metadata : "+ CraftTweakerMC.getItemStack(input).getCount();
    }
}
