package com.zoniic645.scoreboard;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;

public class TeamScore extends WorldSavedData {
    private static Map<ForgeTeam, TeamScore> TeamScoresMap = new HashMap<>();
    private ForgeTeam team = null;
    private long score = 0;

    public TeamScore(String s,ForgeTeam teamIn) {
        super(s);
        this.team = teamIn;
    }

    public ForgeTeam getTeam(){
        return this.team;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return null;
    }

    public static TeamScore getTeamScore(World world,ForgeTeam teamIn) {
        String teamscorenameIn = teamIn.toString();
        MapStorage storage = world.getMapStorage(); //맵스토리지를 가져왔어요
        TeamScore Instance = (TeamScore) storage.getOrLoadData(TeamScore.class, teamscorenameIn); //스토리지에서 팀스코어를 꺼내서 넣어줘요
        if (Instance == null) { //가져왔는데 null이니까 없다는거
            Instance = new TeamScore(teamscorenameIn,teamIn); //없으니까 일단 하나 만든다
            storage.setData(teamscorenameIn, Instance); //맵 스토리지에다가 팀스코어.tostring(dataIdentifier)이랑 팀스코어(data)를 넣어준다. 와! 완성!
        }
        return Instance;
    }

    //점수 더함
    public void addScore(int add) {
        score += add;
    }

    //점수 반환
    public long getScore() {
        return score;
    }
}
