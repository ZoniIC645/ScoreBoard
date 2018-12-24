package com.zoniic645.scoreboard.tileentity;

import com.zoniic645.scoreboard.TeamScore;
import com.zoniic645.scoreboard.score.FluidScore;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityFluidScoreCounter extends BaseScoreCounter implements IFluidTank, ITickable {
    public FluidTank tank = new FluidTank(1000);

    @Override
    public void update() {
        if (this.getFluidAmount() > 0)
            this.drain(this.getFluidAmount(), true);
    }

    @Override
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) this.tank;
        return super.getCapability(capability, facing);
    }

    @Override
    public FluidStack getFluid() {
        return tank.getFluid();
    }

    @Override
    public int getFluidAmount() {
        return tank.getFluidAmount();
    }

    @Override
    public int getCapacity() {
        return tank.getCapacity();
    }

    @Override
    public FluidTankInfo getInfo() {
        return tank.getInfo();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return tank.fill(resource,doFill);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        FluidStack resource = new FluidStack(this.getFluid(),this.getFluidAmount());
        float score = resource.amount * FluidScore.getScore(resource.getFluid());
        TeamScore.get(world).addScore(team, (int)score);
        //System.out.println("score" + resource.amount + "*" + FluidScore.getScore(resource.getFluid()) + "=" + resource.amount * FluidScore.getScore(resource.getFluid()));
        return tank.drain(maxDrain, doDrain);
    }
}
