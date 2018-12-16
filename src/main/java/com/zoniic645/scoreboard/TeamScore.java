package com.zoniic645.scoreboard;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class TeamScore extends WorldSavedData {
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";//색좀 줘보려고. 의미있는건 아님
    private ForgeTeam team = null;
    private long score = 0;

    public TeamScore(String s, ForgeTeam teamIn) { //생성자에서는 이름과 팀을 받아서 팀을 적용시킴
        super(s);
        this.team = teamIn;
    }

    public ForgeTeam getTeam() {
        return this.team;
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
        System.out.println(ANSI_WHITE_BACKGROUND + "WorldSavedData : NBT데이터 뽑아서 적용시킹 : " + compound.getShort("teamUID") + ANSI_RESET);

        team = Universe.get().getTeam(compound.getShort("teamUID")); //팀 UID뽑아서 적용시킴
        score = compound.getLong("score"); //스코어 뽑아서 적용시킴
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        System.out.println(ANSI_WHITE_BACKGROUND + "WorldSavedData : NBT 데이터에 저장함 : " + team.getUID() + ANSI_RESET);

        compound.setShort("teamUID", team.getUID()); //팀을 저장할거니까 UID값을 넣어줌
        compound.setLong("score", score); //스코어니까 long을 넣어줌. 둘다 키값은 아무거나 넣음
        return compound;
    }

    public static TeamScore getTeamScore(World world, ForgeTeam teamIn) {
        System.out.println("팀을 반환합니다");
        String teamscorenameIn = teamIn.toString();
        if (world == null)
            System.out.println("world가 null이잖아 빡대가리새끼야");
        else
            System.out.println("난 빡대가리가 아니였어");
        MapStorage storage = world.getMapStorage(); //맵스토리지를 가져왔어요
        TeamScore Instance = (TeamScore) storage.getOrLoadData(TeamScore.class, teamscorenameIn); //스토리지에서 팀스코어를 꺼내서 넣어줘요
        if (Instance == null) { //가져왔는데 null이니까 없다는거
            System.out.println("없어서 새로 하나 만듬");
            Instance = new TeamScore(teamscorenameIn, teamIn); //없으니까 일단 하나 만든다
            storage.setData(teamscorenameIn, Instance); //맵 스토리지에다가 팀스코어.tostring(dataIdentifier)이랑 팀스코어(data)를 넣어준다. 와! 완성!
        }
        return Instance;
    }

    //점수 더함
    public void addScore(int add) {
        score += add;
        markDirty();
    }

    //점수 반환
    public long getScore() {
        return score;
    }
}
