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
		//board = manager.getNewScoreboard();
		board = manager.getMainScoreboard(); // TODO: remove this temp workaround
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
		int minTeams = 2; // TODO: Set to 1 for debugging, set to 2 for production
		if(teams.size() < minTeams) {
			sender.sendMessage("You cannot start the game with less than two teams");
			return;
		}

		sender.sendMessage("1");
		// Empty the scoreboard
		//this.board = manager.getNewScoreboard(); // /- TODO: remove this temp workaround
		Objective od = this.board.getObjective(DisplaySlot.SIDEBAR);
		if(od != null)
			od.unregister();
		for(Team t : this.board.getTeams()) {
			t.unregister();
		} // -/ TODO: remove this temp workaround
		sender.sendMessage("2");
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
			p.setSaturation(20.0f);
			p.setFoodLevel(20);
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
