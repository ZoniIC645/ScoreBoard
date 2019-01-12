package com.zoniic645.scoreboard.compat.crafttweaker;

import com.zoniic645.scoreboard.score.ScoreHandler;

import crafttweaker.IAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

public class ActionScore<T> implements IAction {
	
    private final float quadraticTerm;
    private final float linearTerm;
    private final float constantTerm;

    private final T input;
    private final int score;

    public ActionScore(float quadraticTerm, float linearTerm, float constantTerm, T stack, int score) {
    	this.quadraticTerm = quadraticTerm;
    	this.linearTerm = linearTerm;
    	this.constantTerm = constantTerm;
        this.score = score;
        this.input = stack;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void apply() {
    	if(input instanceof ItemStack) {
        	ScoreHandler.ITEM.addScore((ActionScore<ItemStack>) this);
    	} else if(input instanceof Fluid) {
        	ScoreHandler.FLUID.addScore((ActionScore<Fluid>) this);
    	}
    }

    @Override
    public String describe() {
    	StringBuilder sb = new StringBuilder();
    	if(input instanceof Fluid) {
    		Fluid fluid = (Fluid) input;
    		sb.append("Add fluid: ");
    		sb.append(fluid.getUnlocalizedName());
    	} else if(input instanceof ItemStack) {
    		ItemStack stack = (ItemStack) input;
    		sb.append("Add item: ");
    		sb.append(stack.getUnlocalizedName());
    		sb.append(":");
    		sb.append(stack.getMetadata());
    	}
    	sb.append(" function: ");
    	sb.append(quadraticTerm);
    	sb.append("x^2+");
    	sb.append(linearTerm);
    	sb.append("x+");
    	sb.append(constantTerm);
    	
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof ActionScore) {
    		return ((ActionScore<?>) obj).getInput().equals(input);
    	} else if(input instanceof ItemStack && obj instanceof ItemStack) { // Can't check Item of ItemStack with equals()
    		ItemStack inputItem = (ItemStack) input;
    		ItemStack objItem = (ItemStack) obj;
    		return inputItem.getItem() == objItem.getItem()
    				&& (objItem.getMetadata() == OreDictionary.WILDCARD_VALUE
    				|| objItem.getMetadata() == inputItem.getMetadata());
    	}
    	
    	return obj.equals(input);
    }

	public float getQuadraticTerm() {
		return quadraticTerm;
	}

	public float getLinearTerm() {
		return linearTerm;
	}

	public float getConstantTerm() {
		return constantTerm;
	}

	public T getInput() {
		return input;
	}

	public float getScore() {
		return score;
	}
}
