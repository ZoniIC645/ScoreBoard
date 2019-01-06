package com.zoniic645.scoreboard.tileentity;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import com.zoniic645.scoreboard.score.TeamScore;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BaseScoreCounter extends TileEntity {
    public ForgeTeam team;

    public ForgeTeam getTeam() {
        return team;
    }

    //점수 반환
    public long getScore() {
        return TeamScore.get(world).getScore(team);
    }

    //팀스코어를 받아와서 넣어주자
    public void setTeam(ForgeTeam teamIn) {
        team = teamIn;
        markDirty();
        System.out.println("팀 세팅함");
    }

    //타일엔티티의 팀을 월드에 쓴다.
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if(!world.isRemote)
        {
            System.out.println("NBT데이터를 월드에 작성함 UID : " + team.getUID());
            super.writeToNBT(compound);
            compound.setShort("team", team.getUID()); //short를 쳐넣어둠
        }
        return compound;
    }

    //팀을 월드에서 받아온다.
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        System.out.println("NBT데이터에서 읽어옴 UID : " + compound.getShort("team"));
        super.readFromNBT(compound);
        //팀 uuid(short)값을 가져와서 저장
        team = Universe.get().getTeam(compound.getShort("team"));
    }
}
