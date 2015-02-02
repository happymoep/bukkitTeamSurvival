package com.github.maride.bukkitTeamSurvival;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class BukkitTeamSurvival extends JavaPlugin {
	
	private BukkitTeamSurvivalCommandExecutor cmdExecutor;
	ScoreboardManager	manager	= null;	// Assigned when plugin gets enabled
	Scoreboard			board	= null;	// Assigned when plugin gets enabled
	
	HashMap<UUID, String>	playerTeams;
	HashMap<String, ChatColor>	teamColors;
	//ArrayList<BtsTeam>		teams;
	
	boolean	showLabels	= true;
	int		teamLives	= 2;
	boolean	loyalty		= false;
	boolean	teamHit		= false;
	
	public void onEnable() {
		// Load HashMap that stores a teamname for each Player
		// TODO: Load HashMap from file (if available) instead of creating a new one
		playerTeams = new HashMap<UUID, String>();
		
		// Register command handler
		cmdExecutor = new BukkitTeamSurvivalCommandExecutor(this);
		getCommand("ts").setExecutor(cmdExecutor);
		
		// Get the scoreboard to be used
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
	}

	public void onDisable() {
		// Save HashMap that stores  a teamname for each Player
		// TODO: Save HashMap to file

		/* TODO:
		 * 	Clear scoreboard (destroy scoreboard?)
		 * 	Revert team setup
		 */
	}
	
	public void setPlayerTeam(UUID uuid, String teamname) {
		if(teamname != null)
			playerTeams.put(uuid, teamname);
		else
			playerTeams.remove(uuid);
	}
	
	public void setTeamColor(String teamname, ChatColor color) {
		teamColors.put(teamname, color);
	}
	
	public void startGame() {
		/* TODO:
		 *  Clear inventory
		 *  Clear hunger
		 *  Clear HP and XP
		 *  Spread teams (!)
		 *  Set gamemode to Survival
		 */
	}
	
	public void endGame() {
		/* TODO:
		 *  Clear inventory
		 *  Set gamemode to Spectator
		 *  Teleport to lobby
		 *  Remove scoreboard
		 */
	}

}
