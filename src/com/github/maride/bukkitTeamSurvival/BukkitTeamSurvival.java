package com.github.maride.bukkitTeamSurvival;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitTeamSurvival extends JavaPlugin {
	
	private BukkitTeamSurvivalCommandExecutor cmdExecutor;
	
	HashMap<UUID, String>		playerTeams	= null;
	HashMap<String, ChatColor>	teamColors	= null;
	
	String	lobbyWorldName;
	BtsLobby lobby = null;
	
	private void loadPluginConfig() {
		lobbyWorldName = "BtsLobbyWorld"; // TODO: Needs to be loaded from config
	}
	
	private void savePluginConfig() {
		//
	}
	
	@SuppressWarnings("unchecked")
	private void loadData() {
		// Load HashMap for player-team and team-color association from file if files exist
		try {
			playerTeams = (HashMap<UUID, String>) FileManager.load("playerTeams.bin");
		}
		catch (Exception e) {
			this.getLogger().info("There was an error loading \"playerTeams.bin\". Creating new (empty) database instead.");
		}
		try {
			teamColors = (HashMap<String, ChatColor>) FileManager.load("teamColors.bin");
		}
		catch (Exception e) {
			this.getLogger().info("There was an error loading \"teamColors.bin\". Creating new (empty) database instead.");
		}
		// Create new HashMap objects in case the file loading did not work
		if(playerTeams == null) {
			playerTeams = new HashMap<UUID, String>();
		}
		if(teamColors == null) {
			teamColors = new HashMap<String, ChatColor>();
		}
	}
	
	private void saveData() {
		// Save HashMap for player-team and team-color association to file
		try {
			FileManager.save(playerTeams, "playerTeams.bin");
		}
		catch (Exception e) {
			this.getLogger().info("Could not store database in file \"playerTeams.bin\".");
		}
		try {
			FileManager.save(teamColors, "teamColors.bin");
		}
		catch (Exception e) {
			this.getLogger().info("Could not store database in file \"teamColors.bin\".");
		}
	}
	
	public void onEnable() {
		loadPluginConfig();
		loadData();
		
		// Register command handler
		cmdExecutor = new BukkitTeamSurvivalCommandExecutor(this);
		getCommand("bts").setExecutor(cmdExecutor);
		getCommand("btss").setExecutor(cmdExecutor);
		
		// Load/Create lobby world
		lobby = new BtsLobby(this, lobbyWorldName);
	}

	public void onDisable() {
		savePluginConfig();
		saveData();
	}
	
	public void setPlayerTeam(UUID uuid, String teamname) {
		if(teamname != null)
			playerTeams.put(uuid, teamname);
		else
			playerTeams.remove(uuid);
	}
	
	public ChatColor getTeamColor(String teamname) {
		return teamColors.get(teamname);
	}
	
	public void setTeamColor(String teamname, ChatColor color) {
		teamColors.put(teamname, color);
	}
	
	public void startGame(CommandSender sender) {
		lobby.start(sender);
	}
}
