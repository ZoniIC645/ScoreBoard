package com.zoniic645.scoreboard.score;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TeamScore extends WorldSavedData {
    private static Map<ForgeTeam, Long> teamMap = new HashMap<>();  //키값으로 포지팀을 받고 value로 long(score)를 냄. 이게 점수를 관리함

    public static final String DATA_NAME = "teamscore";

    public TeamScore() {
        super(DATA_NAME);
    }

    public TeamScore(String s) {
        super(s);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return super.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList("teams", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound comp = list.getCompoundTagAt(i);
            ForgeTeam team = Universe.get().getTeam(comp.getShort("teamUID")); //팀 UID뽑아서 적용시킴
            long score = comp.getLong("score"); //스코어 뽑아서 적용시킴
            teamMap.put(team, score);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        Iterator<ForgeTeam> iter = teamMap.keySet().iterator();
        while (iter.hasNext()) {
            ForgeTeam forgeTeam = iter.next();
            NBTTagCompound tag = new NBTTagCompound();
            tag.setShort("teamUID", forgeTeam.getUID()); //팀을 저장할거니까 UID값을 넣어줌
            tag.setLong("score", teamMap.get(forgeTeam)); //스코어니까 long을 넣어줌. 둘다 키값은 아무거나 넣음
            list.appendTag(tag);
        }
        compound.setTag("teams", list);
        return compound;
    }
    
    public Map<ForgeTeam, Long> getScore() {
        return teamMap;
    }

    //점수 반환
    public long getScore(ForgeTeam teamIn) {
        return teamMap.get(teamIn);
    }

    //점수 더함
    public void addScore(ForgeTeam team, int add) {
        if (teamMap.containsKey(team)) {
            teamMap.put(team, teamMap.get(team) + add);
        } else {
            teamMap.put(team, (long) add);
        }
        System.out.println(teamMap.get(team));
        markDirty();
    }

    public static TeamScore get(World world) {
        MapStorage storage = world.getMapStorage();
        TeamScore instance = (TeamScore) storage.getOrLoadData(TeamScore.class, DATA_NAME);

        if (instance == null) {
            instance = new TeamScore();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }
}
