package com.github.maride.bukkitTeamSurvival;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class BukkitTeamSurvival extends JavaPlugin {
	
	private BukkitTeamSurvivalCommandExecutor cmdExecutor;
	ScoreboardManager	manager	= null;	// Assigned when plugin gets enabled
	Scoreboard			board	= null;	// Assigned when plugin gets enabled
	
	HashMap<UUID, String>		playerTeams;
	HashMap<String, ChatColor>	teamColors;
	//ArrayList<BtsTeam>		teams;
	
	boolean	showLabels	= false;
	int		teamLives	= 2;
	boolean	loyalty		= false;
	boolean	teamHit		= false;
	
	public void onEnable() {
		// Load HashMap that stores a teamname for each Player
		// TODO: Load HashMap from file (if available) instead of creating a new one
		playerTeams = new HashMap<UUID, String>();
		teamColors = new HashMap<String, ChatColor>();
		
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
		// List all players online and participating
		// List all teams that participate in this match
		ArrayList<UUID>		players	= new ArrayList<UUID>();
		ArrayList<String>	teams	= new ArrayList<String>();
		for(Player p : Bukkit.getOnlinePlayers()) {
			String team = this.playerTeams.get(p.getUniqueId());
			if(team != null) {
				players.add(p.getUniqueId());
				boolean bFound = false;
				for(String s : teams) {
					if(team == s)
						bFound = true;
				}
				if(!bFound)
					teams.add(team);
			}
		}
		// Empty the scoreboard
		this.board = manager.getNewScoreboard();
		// Setup the team structure and details of scoreboard
		for(String s : teams) {
			Team t = this.board.registerNewTeam(s);
			ChatColor color = teamColors.get(s);
			if(color != null) {
				t.setPrefix(color.toString());
			}
			NameTagVisibility v = this.showLabels ? NameTagVisibility.ALWAYS : NameTagVisibility.HIDE_FOR_OTHER_TEAMS;
			t.setNameTagVisibility(v);
			t.setAllowFriendlyFire(this.teamHit);
			for(UUID uuid : players) {
				if(this.playerTeams.get(uuid) == s)
				t.addPlayer(Bukkit.getPlayer(uuid));
			}
		}
		Objective o = this.board.registerNewObjective("Leben", "health");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		// Set the scoreboard for all players online
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(this.board);
		}
		
		// Put all players in gamemode 'Spectator' so players not participating are not in the way
		
		// For each player participating do:
		// +Clear inventory and XP
		// +Fill hunger and hp
		// +Spread teams
		// +Put player into gamemode 'Survival'
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
