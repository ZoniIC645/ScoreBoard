package com.zoniic645.scoreboard.tileentity;

import com.zoniic645.scoreboard.TeamScore;
import com.zoniic645.scoreboard.score.ItemScore;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityItemScoreCounter extends BaseScoreCounter implements IItemHandler {
    /**
     * 별로 중요하지 않다. 여기서는 아이템 쳐먹는거만 관심있으니까 슬롯 한개만 냉겨둔거뿐임.
     */

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (team != null) {
            if (!simulate) {
                TeamScore.get(world).addScore(team, ItemScore.getScore(stack));
            }
            return ItemStack.EMPTY; // 빈 ItemStack을 반환하면 내가 다쳐먹었단뜻임
        }
        return stack;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return ItemStack.EMPTY; // 우리 인벤토리에는 아이템이 업어요
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return ItemStack.EMPTY; // 빈 ItemStack을 반환하면 아무것도 안뽑혓단뜻임
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    /**
     * 원하는 키값인지 확인한다. EnumFacing은 null일 수도 있다는 점에 유의.
     * super.hasCapability를 콜해준 이유는 이벤트로 타일엔티티에 capability를 붙이는 또라이새끼가 있을까봐다.
     * 또라이도 모더 아닌건 아니고 우리도 어느정도 또라이니깐 우리 모두 또라이를 존중해주도록 하자.
     */
    @Override
    public boolean hasCapability(Capability<?> cap, EnumFacing facing) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == cap || super.hasCapability(cap, facing);
    }

    /**
     * 키값에 해당하는 인스턴스를 반환한다. 여기서는 나자신임. EnumFacing은 마찬가지로 null일 수도 있다는 점에 유의.
     * 어디 위쪽에서만 받고싶어요!하면 facing이 EnumFacing.UP일 때만 줘버리면 되고
     * 아래쪽에선 다른 상호작용을 만들고 싶어요!하면 facing이 EnumFacing.DOWN일 때 다른 IItemHandler를 줘버리면 된다.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> cap, EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == cap) {
            return (T) this; // 형변환을 두려워하지 마라. 어차피 키값이 ITEM_HANDLER_CAPABILITY였으니 제네릭 타입 T는 무조건 IItemHandler이다.
        }
        return super.getCapability(cap, facing);
    }
}