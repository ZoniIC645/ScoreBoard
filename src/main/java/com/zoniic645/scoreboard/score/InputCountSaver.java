package com.zoniic645.scoreboard.score;

import com.zoniic645.scoreboard.ScoreBoard;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

public class InputCountSaver extends WorldSavedData {
	
    private static final String DATA_NAME = ScoreBoard.MODID + "_InputCount";

    public InputCountSaver() {
        super(DATA_NAME);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
    	InputCounter.ITEM.readFromNBT(nbt);
    	InputCounter.FLUID.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	InputCounter.ITEM.writeToNBT(compound);
    	InputCounter.FLUID.writeToNBT(compound);
        return compound;
    }
}
