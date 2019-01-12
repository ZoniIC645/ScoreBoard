package com.zoniic645.scoreboard.score;

import java.util.ArrayList;
import java.util.List;

import com.zoniic645.scoreboard.compat.crafttweaker.ActionScore;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class ScoreHandler<T> {

	public static final ScoreHandler<ItemStack> ITEM = new ScoreHandler<>();
	public static final ScoreHandler<Fluid> FLUID = new ScoreHandler<>();

    private List<ActionScore<T>> scoreList = new ArrayList<>();
    
    public void addScore(ActionScore<T> score) {
    	if(score == null) return;
    	scoreList.add(score);
    }

    //점수 받아옴
	public long getScore(T input, long value) {
    	int index = scoreList.indexOf(input);
    	if(index == -1) return 0;
    	ActionScore<T> score = scoreList.get(index);
    	// Convert each term to long to prevent more from overflowing
    	return new Float(score.getQuadraticTerm()*value*value).longValue() + 
    			new Float(score.getLinearTerm()*value).longValue() + 
    			new Float(score.getConstantTerm()).longValue();
    }
}
