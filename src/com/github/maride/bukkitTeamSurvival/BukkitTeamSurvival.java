package com.github.maride.bukkitTeamSurvival;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class BukkitTeamSurvival extends JavaPlugin {
	
	ScoreboardManager	manager	= null;	// Assigned when plugin gets enabled
	Scoreboard			board	= null;	// Assigned when plugin gets enabled
	
	public void onEnable(){ 
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
	}

	public void onDisable(){ 
		/* TODO:
		 * 	Clear scoreboard (destroy scoreboard?)
		 * 	Revert team setup
		 */
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("ts")) {
			if(args.length == 0) {
				sender.sendMessage("Usage: /ts <setup|start|stop>");
				return true;
			}

			if(args[0].equalsIgnoreCase("setup")) {
				if(args.length <= 1) {
					sender.sendMessage("Usage: /ts setup <team|teams>");
					return true;
				}

				if(args[1].equalsIgnoreCase("team")) {
					if(args.length <= 2) {
						sender.sendMessage("Usage: /ts setup team <create|delete|add|remove|color|list>");
						return true;
					}

					if(args[2].equalsIgnoreCase("create")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup team create <Teamname>");
							return true;
						}
						Set<Team> teams = board.getTeams();
						for(Team t : teams) {
							if(t.getName() == args[3]) {
								sender.sendMessage("Team " + args[3] + " exists already");
								return true;
						    }
						}
						board.registerNewTeam(args[3]);
						sender.sendMessage("Team " + args[3] + " created.");
						return true;
					} else if(args[2].equalsIgnoreCase("delete")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup team delete <Teamname>");
							return true;
						}
						Team team = board.getTeam(args[3]);
						if(team == null){
							sender.sendMessage("There is no team named " + args[3]);
							return true;
						}
						else{
							team.unregister();
							sender.sendMessage("Team " + args[3] + " deleted");
							return true;
						}
					} else if(args[2].equalsIgnoreCase("add")) {
						if(args.length <= 4) {
							sender.sendMessage("Usage: /ts setup team add <Teamname> <Player> [Player2 [...]]");
							return true;
						}
						Team team = board.getTeam(args[3]);
						if(team == null){
							sender.sendMessage("There is no team named " + args[3]);
							return true;
						}
						String playersFound = "";
						String playersNotFound = "";
						for (int i = 4; i < args.length; i++) {
							Player p = Bukkit.getPlayer(args[i]);
							if(p == null) {
								playersNotFound += " " + args[i];
							}
							else {
								playersFound += " " + args[i];
								team.addPlayer(p);
							}
						}
						if(playersFound != "")
							sender.sendMessage("Added" + playersFound + " to " + args[3]);
						if(playersFound != "")
							sender.sendMessage("Players not found:" + playersNotFound);
						return true;
					} else if(args[2].equalsIgnoreCase("remove")) {
						if(args.length <= 4) {
							sender.sendMessage("Usage: /ts setup team remove <Teamname> <Player> [Player2 [...]]");
							return true;
						}
						Team team = board.getTeam(args[3]);
						if(team == null){
							sender.sendMessage("There is no team named " + args[3]);
							return true;
						}
						String playersFound = "";
						String playersNotFound = "";
						Set<OfflinePlayer> players = team.getPlayers();
						OfflinePlayer p = null;
						for (int i = 4; i < args.length; i++) {
							for(OfflinePlayer pi : players)
								if(args[i] == pi.getName())
									p = pi;
							if(p == null) {
								playersNotFound += " " + args[i];
							}
							else {
								playersFound += " " + args[i];
								team.removePlayer(p);
							}
						}
						if(playersFound != "")
							sender.sendMessage("Removed" + playersFound + " from " + args[3]);
						if(playersFound != "")
							sender.sendMessage("Players not found:" + playersNotFound);
						return true;
					} else if(args[2].equalsIgnoreCase("color")) {
						if(args.length <= 4) {
							sender.sendMessage("Usage: /ts setup team color <Teamname> <color>");
							return true;
						}
						Team team = board.getTeam(args[3]);
						if(team == null){
							sender.sendMessage("There is no team named " + args[3]);
							return true;
						}
						ChatColor color = ChatColor.valueOf(args[4]);
						//TODO: Set the teams color
						sender.sendMessage("Coloring " + args[3] + " in " + color.toString());
						return true;
					} else if(args[2].equalsIgnoreCase("list")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup team list <Teamname>");
							return true;
						}
						Team team = board.getTeam(args[3]);
						if(team == null){
							sender.sendMessage("There is no team named " + args[3]);
							return true;
						}
						Set<OfflinePlayer> players = team.getPlayers();
						if(players.isEmpty()) {
							sender.sendMessage("Team " + args[3] + " has no members");
							return true;
						}
						String names = "";
						for(OfflinePlayer p : players)
							names += ", " + p.getName();
						names = names.replaceFirst(", ", "");
						sender.sendMessage("Members of " + team.getName() + ":");
						sender.sendMessage(names);
						return true;
					} else {
						sender.sendMessage("ts: No such team command: " + args[2]);
					}
				} else if(args[1].equalsIgnoreCase("teams")) {
					if(args.length <= 2) {
						sender.sendMessage("Usage: /ts setup teams <list|showLabels|teamLives|loyality|teamHit>");
						return true;
					}

					if(args[2].equalsIgnoreCase("list")) {
						sender.sendMessage("Listing teams.");
					} else if(args[2].equalsIgnoreCase("showLabels")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup teams showLabels <yes|no>");
							return true;
						}
						sender.sendMessage("Setting showLabels to " + args[3]);
					} else if(args[2].equalsIgnoreCase("teamLives")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup teams teamLives <number>");
							return true;
						}
						sender.sendMessage("Setting teamLives to " + args[3]);
					} else if(args[2].equalsIgnoreCase("loyality")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup teams loyality <yes|no>");
							return true;
						}
						sender.sendMessage("Setting loyality to " + args[3]);
					} else if(args[2].equalsIgnoreCase("teamHit")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup teams teamHit <yes|no>");
							return true;
						}
						sender.sendMessage("Setting teamHit to " + args[3]);
					} else {
						sender.sendMessage("ts: No such teams command: " + args[2]);
					}
				} else {
					sender.sendMessage("ts: No such setup command: " + args[1]);
				}

			} else if(args[0].equalsIgnoreCase("start")) {
				sender.sendMessage("Starting.");
			} else if(args[0].equalsIgnoreCase("stop")) {
				sender.sendMessage("Stopping.");
			} else {
				sender.sendMessage("ts: No such command: " + args[0]);
			}
			return true;
		}
		return false;
	}

}
