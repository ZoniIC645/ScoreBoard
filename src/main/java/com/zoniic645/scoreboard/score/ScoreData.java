package com.zoniic645.scoreboard.score;

import java.util.ArrayList;
import java.util.List;

public class ScoreData {

	private short UID;
	private String name;
	private long score;
	private List<Player> players = new ArrayList<Player>();
	
	public ScoreData(short UID, String name, long score, List<Player> players) {
		this.UID = UID;
		this.name = name;
		this.score = score;
	}

	public short getUID() {
		return UID;
	}
	
	public String getName() {
		return name;
	}
	
	public long getScore() {
		return score;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public static class Player {
		
		private String UUID;
		private String role;
		
		public Player(String UUID, String role) {
			this.UUID = UUID;
			this.role = role;
		}
		
		public String getUUID() {
			return UUID;
		}
		
		public String getRole() {
			return role;
		}
	}
}
