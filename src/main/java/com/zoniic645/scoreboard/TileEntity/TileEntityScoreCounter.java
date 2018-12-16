package com.zoniic645.scoreboard.TileEntity;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import com.zoniic645.scoreboard.ItemScore;
import com.zoniic645.scoreboard.TeamScore;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityScoreCounter extends TileEntity implements IItemHandler {
    /**
     * ㅋㅋ
     */
    //스코어를 두자
    private TeamScore teamscore = null;

    //팀스코어를 반환해준다
    public TeamScore getTeamscore() {
        return teamscore;
    }

    //팀스코어를 받아와서 넣어주자
    public void setTeamscore(ForgeTeam teamIn) {
        teamscore = TeamScore.getTeamScore(world, teamIn);
        markDirty();
        System.out.println("팀 세팅함");
    }

    //팀스코어를 월드에 쓴다. 이게 타일엔티티 부분이지? 키값은 아무거나 넣어둠
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        System.out.println("NBT데이터를 월드에 작성함 UID : " + teamscore.getTeam().getUID());
        super.writeToNBT(compound);
        compound.setShort("team", teamscore.getTeam().getUID()); //short를 쳐넣어둠
        return compound;
    }

    //팀스코어를 월드에서 받아온다. 키값은 일단 아무거나 넣어둠
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        System.out.println("NBT데이터에서 읽어옴 UID : " + compound.getShort("team"));
        super.readFromNBT(compound);
        //조지게 장황하네. 팀 uuid(short)값을 가져와서 팅스코어를 없고 그걸 넣어주는거임
        teamscore = TeamScore.getTeamScore(world, Universe.get().getTeam(compound.getShort("team")));
        //테스트용
    }
    //근데 이거 왜 작동 안하지. 일단 키값이 둘다 "team"이니까 받아와 줘야 되는거 아니여???

    /**
     * 별로 중요하지 않다. 여기서는 아이템 쳐먹는거만 관심있으니까 슬롯 한개만 냉겨둔거뿐임.
     */

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        System.out.printf("add Score : %d\n", ItemScore.instance().getScore(stack)); //콘솔에서 템 점수 얼마인지 보여주는거임
        return ItemStack.EMPTY; // 빈 ItemStack을 반환하면 내가 다쳐먹었단뜻임
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
    @Override
    public <T> T getCapability(Capability<T> cap, EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == cap) {
            return (T) this; // 형변환을 두려워하지 마라. 어차피 키값이 ITEM_HANDLER_CAPABILITY였으니 제네릭 타입 T는 무조건 IItemHandler이다.
        }
        return super.getCapability(cap, facing);
    }
}