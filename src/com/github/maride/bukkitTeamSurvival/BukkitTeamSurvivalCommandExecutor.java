package com.github.maride.bukkitTeamSurvival;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitTeamSurvivalCommandExecutor implements CommandExecutor{
	
	private BukkitTeamSurvival plugin;
	
	public BukkitTeamSurvivalCommandExecutor(BukkitTeamSurvival plugin) {
		this.plugin = plugin;
	}
	
	private boolean senderIsInMatch(CommandSender sender, Command cmd, String label, String[] args) {
		boolean result = false;
		if(sender instanceof Player) {
			Player p = (Player) sender;
			for(BtsMatch m : plugin.lobby.matches) {
				if(p.getWorld() == m.getWorld()) {
					result = true;
				}
			}
		}
		return result;
	}
	
	private BtsMatch senderGetMatch(CommandSender sender, Command cmd, String label, String[] args) {
		BtsMatch match = null;
		if(sender instanceof Player) {
			Player p = (Player) sender;
			for(BtsMatch m : plugin.lobby.matches) {
				if(p.getWorld() == m.getWorld()) {
					match = m;
				}
			}
		}
		return match;
	}
	
	private boolean printSettings(CommandSender sender, Command cmd, String label, String[] args) {
		if(senderIsInMatch(sender, cmd, label, args)) {
			BtsMatch match = senderGetMatch(sender, cmd, label, args);
			if(match != null) {
				BtsMatchSettings ms = match.getMatchSettings();
				sender.sendMessage("Showing settings for current match:");
				String response = ms.showLabels ? "yes" : "no";
				sender.sendMessage("showLabels: " + response);
				sender.sendMessage("teamLives: " + Integer.toString(ms.teamLives));
				response = ms.loyalty ? "yes" : "no";
				sender.sendMessage("loyalty: " + response);
				response = ms.teamHit ? "yes" : "no";
				sender.sendMessage("teamHit: " + response);
				return true;
			}
			sender.sendMessage("Something went wrong when selecting your world");
			return true;
		}
		else {
			// If sender is not a player (e.g. console) -> show settings for lobby
			sender.sendMessage("Showing settings for lobby:");
			String response = plugin.lobby.tempMS.showLabels ? "yes" : "no";
			sender.sendMessage("showLabels: " + response);
			sender.sendMessage("teamLives: " + Integer.toString(plugin.lobby.tempMS.teamLives));
			response = plugin.lobby.tempMS.loyalty ? "yes" : "no";
			sender.sendMessage("loyalty: " + response);
			response = plugin.lobby.tempMS.teamHit ? "yes" : "no";
			sender.sendMessage("teamHit: " + response);
		}
		return true;
	}
	
	private boolean btsLobby(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length >= 2) {
			if(args[1].equalsIgnoreCase("join")) {
				if(sender instanceof Player) {
					this.plugin.lobby.joinLobby((Player) sender);
					return true;
				}
			}
			else if(args[1].equalsIgnoreCase("leave")) {
				if(sender instanceof Player) {
					this.plugin.lobby.leaveLobby((Player) sender);
					return true;
				}
			}
		}
		sender.sendMessage(ChatColor.RED + "Usage: /" + label + " lobby <join|leave>");
		return true;
	}
	
	private boolean btsTeam(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length >= 2) {
			if(args[1].equalsIgnoreCase("color")) {
				if(args.length >= 3) {
					ChatColor color = plugin.getTeamColor(args[2]);
					if(color == null) {
						sender.sendMessage("No color set for team " + ChatColor.YELLOW + args[2]);
						return true;
					}
					else {
						sender.sendMessage("Team " + color + args[2] + ChatColor.RESET + " is colored " + color.toString());
						return true;
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Usage: /" + label + " team color <teamname>");
					return true;
				}
			}
			else if(args[1].equalsIgnoreCase("list")) {
				if(args.length >= 3) {
					if(senderIsInMatch(sender, cmd, label, args)) {
						BtsMatch m = senderGetMatch(sender, cmd, label, args);
						BtsTeam team = null;
						for(BtsTeam t : m.getTeams()) {
							if(t.getName().equalsIgnoreCase(args[2])) {
								team = t;
							}
						}
						if(team == null) {
							sender.sendMessage(ChatColor.RED + "No team named " + ChatColor.YELLOW + args[2] + ChatColor.RED + " found");
							return true;
						}
						else {
							String names = "";
							for(UUID uuid : team.getPlayerUUIDs()) {
								Player p = Bukkit.getPlayer(uuid);
								names += " " + p.getName();
							}
							names = names.trim();
							sender.sendMessage("Listing members of team " + team.getColor() + args[2]);
							sender.sendMessage(ChatColor.YELLOW + names);
						}
						return true;
					}
					else {
						// TODO: need to get all online players and stuff if we really want to enable this
						sender.sendMessage(ChatColor.RED + "You can only list players on a team from your current match. Go join a match");
						return true;
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Usage: /" + label + " team list <teamname>");
					return true;
				}
			}
		}
		sender.sendMessage(ChatColor.RED + "Usage: /" + label + " team <color|list>");
		return true;
	}
	
	private boolean btsSettings(CommandSender sender, Command cmd, String label, String[] args) {
		return printSettings(sender, cmd, label, args);
	}
	
	private boolean btssLobby(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length >= 2) {
			if(args[1].equalsIgnoreCase("set")) {
				if(args.length == 2) {
					// Set lobbyworld to the world sender is in
					if(sender instanceof Player) {
						World world = ((Player) sender).getWorld();
						String worldname = world.getName();
						// TODO: store worldname for lobbyworldname in the plugin config
						return true;
					}
					else {
						sender.sendMessage("You need to be in a world to call set without a worldname");
						return true;
					}
				}
				else if(args.length > 2) {
					// Set lobbyworld to args[2]
					// TODO: store worldname for lobbyworldname in the plugin config
					return true;
				}
			}
		}
		sender.sendMessage(ChatColor.RED + "Usage: /" + label + " lobby <set>");
		return true;
	}
	
	private boolean btssTeam(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length >= 2) {
			if(args[1].equalsIgnoreCase("add")) {
				if(args.length < 4) {
					sender.sendMessage(ChatColor.RED + "Usage: /" + label + " team add <teamname> <player> [player2 [...]]");
					return true;
				}
				OfflinePlayer[] offlinePlayers	= Bukkit.getServer().getOfflinePlayers();
				String playersNotFound = "";
				for(int i = 4; i < args.length; i++) {
					boolean success = false;
					for(OfflinePlayer p : offlinePlayers) {
						if(args[i].equalsIgnoreCase(p.getName())) {
							plugin.setPlayerTeam(p.getUniqueId(), args[3]);
							success = true;
							break;
						}
					}
					if(!success) {
						playersNotFound += " " + args[i];
					}
				}
				if(playersNotFound != "") {
					sender.sendMessage(ChatColor.RED + "Players not found:" + ChatColor.YELLOW + playersNotFound);
				}
				return true;
			}
			else if(args[1].equalsIgnoreCase("remove")) {
				if(args.length < 4) {
					sender.sendMessage(ChatColor.RED + "Usage: /" + label + " team remove <teamname> <player> [player2 [...]]");
					return true;
				}
				OfflinePlayer[] offlinePlayers	= Bukkit.getServer().getOfflinePlayers();
				String playersNotFound = "";
				for(int i = 4; i < args.length; i++) {
					boolean success = false;
					for(OfflinePlayer p : offlinePlayers) {
						if(args[i].equalsIgnoreCase(p.getName())) {
							plugin.setPlayerTeam(p.getUniqueId(), null);
							success = true;
							break;
						}
					}
					if(!success) {
						playersNotFound += " " + args[i];
					}
				}
				if(playersNotFound != "") {
					sender.sendMessage(ChatColor.RED + "Players not found:" + ChatColor.YELLOW + playersNotFound);
				}
				return true;
			}
			else if(args[1].equalsIgnoreCase("color")) {
				if(args.length < 3) {
					sender.sendMessage(ChatColor.RED + "Usage: /" + label + " team color <teamname> [color]");
					return true;
				}
				else if(args.length < 4) {
					ChatColor color = plugin.getTeamColor(args[2]);
					if(color == null) {
						sender.sendMessage("No color set for team " + ChatColor.YELLOW + args[2]);
						return true;
					}
					else {
						sender.sendMessage("Team " + color + args[2] + ChatColor.RESET + " is colored " + color.toString());
						return true;
					}
				}
				else if(args.length >= 4) {
					ChatColor color = null;
					try {
						color = ChatColor.valueOf(args[3]);
					}
					catch(Exception e) {
						// TODO: Handle this in a better way? Or just get rid of this debug info
						plugin.getLogger().info("Caught exception when parsing ChatColor and safely dumped it :S");
					}
					if((color == null) || (!color.isColor())) {
						sender.sendMessage(ChatColor.RED + "That is not a valid color");
						return true;
					}
					ChatColor oldColor = plugin.getTeamColor(args[2]);
					if(oldColor == null) {
						oldColor = ChatColor.YELLOW;
					}
					plugin.setTeamColor(args[2], color);
					sender.sendMessage("Changing color for " + oldColor + args[2] + ChatColor.RESET + " to " + color + args[2]);
					return true;
				}
			}
		}
		sender.sendMessage(ChatColor.RED + "Usage: /" + label + " team <add|remove|color>");
		return true;
	}
	
	private boolean btssSettings(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length <= 2) {
			//Print settings
			return printSettings(sender, cmd, label, args);
		}
		BtsMatchSettings ms = plugin.lobby.tempMS;
		if(senderIsInMatch(sender, cmd, label, args)) {
			ms = senderGetMatch(sender, cmd, label, args).getMatchSettings();
		}
		if(args[1].equalsIgnoreCase("showLabels")) {
			ms.showLabels = args[2].equalsIgnoreCase("yes") ? true : false;
			return true;
		}
		else if(args[1].equalsIgnoreCase("teamLives")) {
			try {
				int teamLives = Integer.parseInt(args[2]);
				ms.teamLives = teamLives;
			}
			catch(NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "This is not a valid number: " + ChatColor.YELLOW + args[2]);
			}
			return true;
		}
		else if(args[1].equalsIgnoreCase("loyalty")) {
			ms.loyalty = args[2].equalsIgnoreCase("yes") ? true : false;
			return true;
		}
		else if(args[1].equalsIgnoreCase("teamHit")) {
			ms.teamHit = args[2].equalsIgnoreCase("yes") ? true : false;
			return true;
		}
		sender.sendMessage(ChatColor.RED + "Usage: /" + label + " settings [showLabels|teamLives|loyalty|teamHit]");
		return true;
	}
	
	private boolean btssStart(CommandSender sender, Command cmd, String label, String[] args) {
		plugin.lobby.start(sender);
		return true;
	}
	
	private boolean btssStop(CommandSender sender, Command cmd, String label, String[] args) {
		if(senderIsInMatch(sender, cmd, label, args)) {
			BtsMatch match = senderGetMatch(sender, cmd, label, args);
			sender.sendMessage(ChatColor.RED + "Stopping matches is not possible at the moment, sorry. Just start a new one");
			//Get rid of players and unload world first
			//match.freeWorld();
			//remove match from lobby.matches
			return true;
		}
		sender.sendMessage(ChatColor.RED + "You appear not to be in any match");
		return true;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("bts")) {
			if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("lobby")) {
					return btsLobby(sender, cmd, label, args);
				}
				else if(args[0].equalsIgnoreCase("team")) {
					return btsTeam(sender, cmd, label, args);
				}
				else if(args[0].equalsIgnoreCase("settings")) {
					return btsSettings(sender, cmd, label, args);
				}
			}
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <lobby|team|settings>");
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("btss")) {
			if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("lobby")) {
					return btssLobby(sender, cmd, label, args);
				}
				else if(args[0].equalsIgnoreCase("team")) {
					return btssTeam(sender, cmd, label, args);
				}
				else if(args[0].equalsIgnoreCase("settings")) {
					return btssSettings(sender, cmd, label, args);
				}
				else if(args[0].equalsIgnoreCase("start")) {
					return btssStart(sender, cmd, label, args);
				}
				else if(args[0].equalsIgnoreCase("stop")) {
					return btssStop(sender, cmd, label, args);
				}
			}
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <lobby|team|settings|start|stop>");
			return true;
		}
		return false;
	}
}
