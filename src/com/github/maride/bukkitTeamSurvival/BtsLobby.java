package com.github.maride.bukkitTeamSurvival;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class BtsLobby {

	private BukkitTeamSurvival plugin;
	private World world = null;
	
	BtsMatchSettings tempMS = new BtsMatchSettings();
	
	protected	Set<BtsMatch>	matches	= new HashSet<BtsMatch>();
	
	HashMap<UUID, Location> playerLastPosition = new HashMap<UUID, Location>();
	
	public BtsLobby(BukkitTeamSurvival plugin, String lobbyWorldName) {
		this.plugin = plugin;
		WorldCreator wc	= new WorldCreator(lobbyWorldName);
		wc.environment(Environment.NORMAL);
		wc.type(WorldType.NORMAL);
		this.setWorld(Bukkit.createWorld(wc));
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
		this.initialize();
	}

	/**
	 * Initialize the lobby world
	 */
	public void initialize() {
		// TODO: 
	}
	
	public void joinLobby(Player player) {
		if(player.getWorld() != this.world) {
			playerLastPosition.put(player.getUniqueId(), player.getLocation());
			player.teleport(this.world.getSpawnLocation());
		}
	}
	
	public void leaveLobby(Player player) {
		if(player.getWorld() == this.world) {
			Location l = playerLastPosition.get(player.getUniqueId());
			if(l == null) {
				l = Bukkit.getWorlds().get(0).getSpawnLocation();
			}
			if(l != null) {
				player.teleport(l);
			}
		}
	}
	
	public void start(CommandSender sender) {
		// Create a matchname that is not used yet
		//  Will be used as world (folder) name
		String matchname = "BtsMatch";
		String name = "";
		int counter = 0;
		boolean bFoundUnusedName = false;
		while(!bFoundUnusedName) {
			counter++;
			name = matchname + Integer.toString(counter);
			boolean bExists = false;
			for(BtsMatch m : matches) {
				if(m.getWorld().getName().equalsIgnoreCase(name)) {
					bExists = true;
				}
			}
			if(!bExists) {
				bFoundUnusedName = true;
			}
		}
		matchname = name;
		
		// List all players online and participating
		// List all teams that participate in this match
		HashSet<UUID>		players	= new HashSet<UUID>();
		HashSet<BtsTeam>	teams	= new HashSet<BtsTeam>();
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getWorld().equals(this.world)) {
				UUID pUuid = p.getUniqueId();
				String teamName = plugin.playerTeams.get(pUuid);
				BtsTeam team = null;
				if(teamName != null) {
					players.add(pUuid);
					boolean bFound = false;
					for(BtsTeam t : teams) {
						if(teamName == t.getName()) {
							bFound = true;
							team = t;
						}
					}
					if(!bFound) {
						team = new BtsTeam(teamName);
						teams.add(team);
					}
					team.addPlayerUUID(pUuid);
				}
			}
		}
		
		// If team setup invalid inform players in the lobby
		int minTeams = 2;
		if(teams.size() < 1) { // Set to 1 for debugging to get a compiler warning (unused variable) as a reminder
			for(Player p : this.world.getPlayers()) {
				p.sendMessage("Need at least " + minTeams + " teams to start");
			}
			return;
		}
		
		// Create an empty scoreboard for the new match
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		// Setup the team structure and details of scoreboard
		for(BtsTeam bt : teams) {
			Team t = board.registerNewTeam(bt.getName());
			ChatColor color = plugin.teamColors.get(bt.getName());
			if(color != null) {
				t.setPrefix(color.toString());
			}
			NameTagVisibility v = this.tempMS.showLabels ? NameTagVisibility.ALWAYS : NameTagVisibility.HIDE_FOR_OTHER_TEAMS;
			t.setNameTagVisibility(v);
			t.setAllowFriendlyFire(this.tempMS.teamHit);
			for(UUID uuid : players) {
				if(plugin.playerTeams.get(uuid) == bt.getName())
				t.addPlayer(Bukkit.getPlayer(uuid));
			}
		}
		Objective o = board.registerNewObjective("Leben", "health");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		
		// Set up worldborder
		World world = Bukkit.createWorld(new WorldCreator(matchname));
		WorldBorder wb = world.getWorldBorder();
		wb.reset();
		wb.setCenter(0.0d, 0.0d);
		wb.setSize(32.0d * 16.0d);	// Counted in whole chunks (Multiple of 16 Blocks)
		
		// Create the BtsMatch and add it to the lobbies match list
		BtsMatch match = new BtsMatch();
		
		match.setWorld(world);
		match.setTeams(teams);
		match.setBoard(board);
		match.setMatchSettings(new BtsMatchSettings(this.tempMS));
		
		this.matches.add(match);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			// Put all players in gamemode 'Spectator' so players not participating are not in the way
			p.setGameMode(GameMode.SPECTATOR);
			// Set the scoreboard for all players online
			p.setScoreboard(board);
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
			p.teleport(match.getWorld().getSpawnLocation());
			// temporary fix: place the player on ground (surface) to avoid falling, suffocating etc.
			Location playerLocation = p.getLocation();
			Location surfaceLocation = p.getWorld().getHighestBlockAt(playerLocation).getLocation();
			p.teleport(surfaceLocation);
			// end of temporary fix
			p.setGameMode(GameMode.SURVIVAL);
		}
	}
}
