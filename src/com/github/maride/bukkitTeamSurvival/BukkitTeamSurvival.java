package com.github.maride.bukkitTeamSurvival;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
	
	HashMap<UUID, String>		playerTeams	= null;
	HashMap<String, ChatColor>	teamColors	= null;
	//ArrayList<BtsTeam>		teams;
	
	boolean	showLabels	= false;
	int		teamLives	= 2;
	boolean	loyalty		= false;
	boolean	teamHit		= false;
	
	@SuppressWarnings("unchecked")
	private void loadData() {
		// Load HashMap for player-team and team-color association from file if files exist
		try {
			playerTeams = (HashMap<UUID, String>) FileManager.load("playerTeams.bin");
		}
		catch (Exception e) {
			this.getLogger().info("The file \"playerTeams.bin\" does not exist. Creating new (empty) database instead.");
		}
		try {
			teamColors = (HashMap<String, ChatColor>) FileManager.load("teamColors.bin");
		}
		catch (Exception e) {
			this.getLogger().info("The file \"teamColors.bin\" does not exist. Creating new (empty) database instead.");
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
		loadData();
		
		// Register command handler
		cmdExecutor = new BukkitTeamSurvivalCommandExecutor(this);
		getCommand("bts").setExecutor(cmdExecutor);
		
		// Get/Create the scoreboard to be used
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
	}

	public void onDisable() {
		saveData();

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
	
	public void startGame(CommandSender sender) {
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
		
		// If team setup invalid inform command sender
		int minTeams = 2;
		if(teams.size() < 1) { // Set to 1 for debugging to get a compiler warning as a reminder
			sender.sendMessage("You cannot start the game with less than two teams");
			return;
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
		
		// Set up worldborder
		World world = null;
		if(sender instanceof Player) {
			world = ((Player) sender).getWorld();
		}
		else {
			if(!players.isEmpty())
				world = Bukkit.getPlayer(players.get(0)).getWorld();
		}
		if(world == null)
			return;
		WorldBorder wb = world.getWorldBorder();
		wb.reset();
		wb.setCenter(0.0d, 0.0d);
		wb.setSize(32.0d * 16.0d);	// Counted in whole chunks (Multiple of 16 Blocks)
		
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			// Put all players in gamemode 'Spectator' so players not participating are not in the way
			p.setGameMode(GameMode.SPECTATOR);
			// Set the scoreboard for all players online
			p.setScoreboard(this.board);
		}
		
		// For each player participating do
		// Clear inventory and XP, fill hunger and hp, spread teams, put player into gamemode 'Survival'
		for(UUID uuid : players) {
			Player p = Bukkit.getPlayer(uuid);
			p.getInventory().clear();
			// creates an array of 4 ItemStacks, initialized with null, thus everything is set to nothing - Bone008 on bukkit.org
			p.getInventory().setArmorContents(new ItemStack[4]);	// clear does not clear armor (untested statement, read on forums)
			p.setExp(0.0f);
			p.setExhaustion(0.0f);
			p.setFoodLevel(20);	// Set food before saturation because: Saturation level can never exceed hunger level (wiki statement)
			p.setSaturation(20.0f);
			p.setHealth(p.getMaxHealth());
			// TODO: spread
			p.setGameMode(GameMode.SURVIVAL);
		}
		
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
