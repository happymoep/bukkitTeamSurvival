package com.github.maride.bukkitTeamSurvival;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitTeamSurvival extends JavaPlugin {

	public void onEnable(){ 
		System.out.println("BukkitTeamSurvival enabled.");
	}

	public void onDisable(){ 
		System.out.println("BukkitTeamSurvival disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		System.out.println(cmd.getName());
		if(cmd.getName().equalsIgnoreCase("ts")) {
			if(args.length == 0) {
				sender.sendMessage("Usage: /ts {setup|start|stop}");
				return true;
			}

			if(args[0].equalsIgnoreCase("setup")) {
				if(args.length <= 1) {
					sender.sendMessage("Usage: /ts setup {team|teams}");
					return true;
				}

				if(args[1].equalsIgnoreCase("team")) {
					if(args.length <= 2) {
						sender.sendMessage("Usage: /ts setup team {add|remove|put|pull|color|list}");
						return true;
					}

					if(args[2].equalsIgnoreCase("add")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup team add <Teamname>");
							return true;
						}
						sender.sendMessage("Adding " + args[3]);
					} else if(args[2].equalsIgnoreCase("remove")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup team remove <Teamname>");
							return true;
						}
						sender.sendMessage("Removing " + args[3]);
					} else if(args[2].equalsIgnoreCase("put")) {
						if(args.length <= 4) {
							sender.sendMessage("Usage: /ts setup team put <Player> <Teamname>");
							return true;
						}
						sender.sendMessage("Putting " + args[3] + " into " + args[4]);
					} else if(args[2].equalsIgnoreCase("pull")) {
						if(args.length <= 4) {
							sender.sendMessage("Usage: /ts setup team pull <Player> <Teamname>");
							return true;
						}
						sender.sendMessage("Pulling " + args[3] + " from " + args[4]);
					} else if(args[2].equalsIgnoreCase("color")) {
						if(args.length <= 4) {
							sender.sendMessage("Usage: /ts setup team color {color} <Teamname>");
							return true;
						}
						sender.sendMessage("Coloring " + args[4] + " in " + args[3]);
					} else if(args[2].equalsIgnoreCase("list")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup team list <Teamname>");
							return true;
						}
						sender.sendMessage("Listing players of team " + args[3]);
					} else {
						sender.sendMessage("ts: No such team command: " + args[2]);
					}
				} else if(args[1].equalsIgnoreCase("teams")) {
					if(args.length <= 2) {
						sender.sendMessage("Usage: /ts setup teams {list|showLabels|teamLives|loyality|teamHit}");
						return true;
					}

					if(args[2].equalsIgnoreCase("list")) {
						sender.sendMessage("Listing teams.");
					} else if(args[2].equalsIgnoreCase("showLabels")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup teams showLabels {yes|no}");
							return true;
						}
						sender.sendMessage("Setting showLabels to " + args[3]);
					} else if(args[2].equalsIgnoreCase("teamLives")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup teams teamLives {number}");
							return true;
						}
						sender.sendMessage("Setting teamLives to " + args[3]);
					} else if(args[2].equalsIgnoreCase("loyality")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup teams loyality {yes|no}");
							return true;
						}
						sender.sendMessage("Setting loyality to " + args[3]);
					} else if(args[2].equalsIgnoreCase("teamHit")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: /ts setup teams teamHit {yes|no}");
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
