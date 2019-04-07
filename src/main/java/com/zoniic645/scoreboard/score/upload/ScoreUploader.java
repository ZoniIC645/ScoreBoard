package com.zoniic645.scoreboard.score.upload;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;
import com.zoniic645.scoreboard.ScoreBoard;
import com.zoniic645.scoreboard.score.ScoreConfig;
import com.zoniic645.scoreboard.score.ScoreData;
import com.zoniic645.scoreboard.score.TeamScore;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ScoreUploader {
	
	private static Gson gson = new Gson();
	
	private static GoogleCredential scoped;
	private static String token;

	public static void init() {
		try {
			FileInputStream serviceAccount = new FileInputStream("credential.json");
			GoogleCredential googleCred = GoogleCredential.fromStream(serviceAccount);
			scoped = googleCred.createScoped(
			    Arrays.asList(
			      "https://www.googleapis.com/auth/firebase.database",
			      "https://www.googleapis.com/auth/userinfo.email"
			    )
			);
		} catch(IOException e) {
			ScoreBoard.logger.log(Level.ERROR, "An error ocurred while getting the token. If you are not going to use this feature, just ignore this.");
			ScoreBoard.logger.log(Level.ERROR, e.getMessage());
		}
	}
	
	public static void update() {
		if(!send()) {
			refresh();
			send();	
		}
	}
	
	private static void refresh() {
		try {
			scoped.refreshToken();
			token = scoped.getAccessToken();
		} catch (IOException e) {
			ScoreBoard.logger.log(Level.ERROR, "An error ocurred while refreshing the token.");
			ScoreBoard.logger.log(Level.ERROR, e.getMessage());
		}
	}
	
	private static boolean send() {
		try {
			URL url = new URL("https://" + ScoreConfig.projectId + ".firebaseio.com/" + ScoreConfig.path + ".json?access_token=" + token);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("PUT");
			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
			out.write(getNewData());
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
			return true;
		} catch (IOException e) {
			ScoreBoard.logger.log(Level.ERROR, "An error ocurred while sending the score.");
			ScoreBoard.logger.log(Level.ERROR, e.getMessage());
		}
		return false;
	}
	
	private static String getNewData() {
		List<ScoreData> dataList = new ArrayList<ScoreData>();
		
		World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
		Map<ForgeTeam, Long> scoreMap = TeamScore.get(world).getScore();
		
		Iterator<ForgeTeam> iter = scoreMap.keySet().iterator();
		while(iter.hasNext()) {
			ForgeTeam team = iter.next();
			dataList.add(new ScoreData(team.getUID(), team.getTitle().getUnformattedText(), scoreMap.get(team)/*, getPlayerList(team.players)*/));
		}
		
		return gson.toJson(dataList);
	}
	
	/*
	private static List<ScoreData.Player> getPlayerList(Map<ForgePlayer, EnumTeamStatus> playerMap) {
		List<ScoreData.Player> players = new ArrayList<ScoreData.Player>();
		
		Iterator<ForgePlayer> iter = playerMap.keySet().iterator();
		while(iter.hasNext()) {
			ForgePlayer player = iter.next();
			EnumTeamStatus role = playerMap.get(player);
			if(role == EnumTeamStatus.OWNER || role == EnumTeamStatus.MOD || role == EnumTeamStatus.MEMBER) {
				players.add(new ScoreData.Player(player.entityPlayer.getUniqueID().toString(), role.toString()));
			}
		}
		
		return players;
	}
	*/
}
