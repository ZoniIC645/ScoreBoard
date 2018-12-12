package com.zoniic645.scoreboard;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;

public class TeamScore extends WorldSavedData {
    private static Map<ForgeTeam, TeamScore> TeamScores = new HashMap<>();
    private static TeamScore INSTANCE = null;
    private ForgeTeam team;
    private long score;

    public TeamScore(String mapName, ForgeTeam teamln) {
        super(mapName);
        this.team = teamln;
        TeamScores.put(teamln,this);
    }

    public static TeamScore getInstance() {
        return INSTANCE;
    }


    public static TeamScore getTeamScore(ForgeTeam teamln){
            return TeamScores.get(teamln);
    }

    public ForgeTeam getTeam() {
        return team;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.writeToNBT(compound);
        //compound.setLong("counter", score);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.readFromNBT(nbt);
        score = nbt.getLong("counter");
    }

    //점수 더함
    public void addScore(int add) {
        score += add;
        markDirty();
    }

    public long getScore() {
        return score;
    }
}
