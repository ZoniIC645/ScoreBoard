package com.zoniic645.scoreboard;

//
public class TeamScore {
    private long score = 0;

    //점수 더함
    public void addScore(int add) {
        score += add;
    }

    public long getScore() {
        return score;
    }
}
