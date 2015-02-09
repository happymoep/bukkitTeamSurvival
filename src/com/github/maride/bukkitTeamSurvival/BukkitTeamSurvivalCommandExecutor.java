package com.github.maride.bukkitTeamSurvival;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BukkitTeamSurvivalCommandExecutor implements CommandExecutor{
	
	private BukkitTeamSurvival plugin;
	
	public BukkitTeamSurvivalCommandExecutor(BukkitTeamSurvival plugin) {
		this.plugin = plugin;
	}
	
//	private void btsSendMessage(CommandSender sender, Command cmd, String label, String[] args) {
//		// TODO
//	}
	
	/**
	 * @return true if usage information was displayed to sender and false if the input still needs to be handled elsewhere
	 */
	private boolean displayUsage(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("bts")) {
			if(args.length == 0) {
				sender.sendMessage("Usage: " + cmd.getName() + " <setup|start|stop>");
				return true;
			}
			if(args[0].equalsIgnoreCase("setup")) {
				if(args.length <= 1) {
					sender.sendMessage("Usage: " + cmd.getName() + " setup <team|teams>");
					return true;
				}
				if(args[1].equalsIgnoreCase("team")) {
					if(args.length <= 2) {
						sender.sendMessage("Usage: " + cmd.getName() + " setup team <add|remove|color|list>");
						return true;
					}
					if(args[2].equalsIgnoreCase("add")) {
						if(args.length <= 4) {
							sender.sendMessage("Usage: " + cmd.getName() + " setup team add <Teamname> <Player> [Player2 [...]]");
							return true;
						}
					} else if(args[2].equalsIgnoreCase("remove")) {
						if(args.length <= 4) {
							sender.sendMessage("Usage: " + cmd.getName() + " setup team remove <Teamname> <Player> [Player2 [...]]");
							return true;
						}
					} else if(args[2].equalsIgnoreCase("color")) {
						if(args.length <= 4) {
							sender.sendMessage("Usage: " + cmd.getName() + " setup team color <Teamname> <color>");
							return true;
						}
					} else if(args[2].equalsIgnoreCase("list")) {
						if(args.length <= 3) {
							sender.sendMessage("Usage: " + cmd.getName() + " setup team list <Teamname>");
							return true;
						}
					} else {
						if(args.length >= 3) {
							sender.sendMessage(cmd.getName() + ": No such team command: " + args[2]);
							return true;
						}
					}
				} else if(args[1].equalsIgnoreCase("teams")) {
					if(args.length <= 2) {
						sender.sendMessage("Usage: " + cmd.getName() + " setup teams <list|showLabels|teamLives|loyalty|teamHit>");
						return true;
					}
					if(args[2].equalsIgnoreCase("showLabels")) {
						if(args.length >= 4) {
							if(!args[3].equalsIgnoreCase("yes") && !args[3].equalsIgnoreCase("no")) {
								sender.sendMessage("Argument must be 'yes' or 'no'. (Not " + args[3] + ")");
								return true;
							}
						}
					} else if(args[2].equalsIgnoreCase("teamLives")) {
						if(args.length >= 4) {
							int lives = Integer.parseInt(args[3]);
							if(lives<0) {
								sender.sendMessage("Argument must be equal or greater than 0. (Not " + args[3] + ")");
								return true;
							}
						}
					} else if(args[2].equalsIgnoreCase("loyalty")) {
						if(args.length >= 4) {
							if(!args[3].equalsIgnoreCase("yes") && !args[3].equalsIgnoreCase("no")) {
								sender.sendMessage("Argument must be 'yes' or 'no'. (Not " + args[3] + ")");
								return true;
							}
						}
					} else if(args[2].equalsIgnoreCase("teamHit")) {
						if(args.length >= 4) {
							if(!args[3].equalsIgnoreCase("yes") && !args[3].equalsIgnoreCase("no")) {
								sender.sendMessage("Argument must be 'yes' or 'no'. (Not " + args[3] + ")");
								return true;
							}
						}
					} else {
						if(args.length >= 3) {
							sender.sendMessage(cmd.getName() + ": No such teams command: " + args[2]);
							return true;
						}
					}
				} else {
					if(args.length >= 2) {
						sender.sendMessage(cmd.getName() + ": No such setup command: " + args[1]);
						return true;
					}
				}
			} else if(args[0].equalsIgnoreCase("start")) {
				// Do not remove this
			} else if(args[0].equalsIgnoreCase("stop")) {
				// Do not remove this
			} else {
				sender.sendMessage(cmd.getName() + ": No such command: " + args[0]);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Handle if usage information should be displayed, otherwise go on
		if(displayUsage(sender, cmd, label, args))
			return true;
		
		if(cmd.getName().equalsIgnoreCase("bts")) {
			if(args.length <= 0) {
				return true;
			}
			if(args[0].equalsIgnoreCase("setup")) {
				if(args.length <= 1) {
					return true;
				}
				if(args[1].equalsIgnoreCase("team")) {
					if(args.length <= 2) {
						return true;
					}
					if(args[2].equalsIgnoreCase("add")) {
						if(args.length <= 4) {
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
						if(playersNotFound != "")
							sender.sendMessage("Players not found:" + playersNotFound);
						return true;
					} else if(args[2].equalsIgnoreCase("remove")) {
						if(args.length <= 4) {
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
						if(playersNotFound != "")
							sender.sendMessage("Players not found:" + playersNotFound);
						return true;
					} else if(args[2].equalsIgnoreCase("color")) {
						if(args.length <= 4) {
							return true;
						}
						ChatColor color = ChatColor.valueOf(args[4]);
//						if(!color.isColor())
//							return true;	// TODO: Inform commandSender of this
						plugin.setTeamColor(args[3], color);
						sender.sendMessage("Coloring " + args[3] + " in " + color.toString());
						return true;
					} else if(args[2].equalsIgnoreCase("list")) {
						if(args.length <= 3) {
							return true;
						}
						sender.sendMessage("Listing players in team " + args[3]);
						return true;
					}
				} else if(args[1].equalsIgnoreCase("teams")) {
					if(args.length <= 2) {
						return true;
					}
					if(args[2].equalsIgnoreCase("list")) {
						sender.sendMessage("Listing teams");
					} else if(args[2].equalsIgnoreCase("showLabels")) {
						if(args.length <= 3) {
							sender.sendMessage("showLabels is set to " + (plugin.showLabels?"YES":"NO"));
							return true;
						}
//						if(!args[3].equalsIgnoreCase("yes") && !args[3].equalsIgnoreCase("no")) {
//							return true;
//						}
						sender.sendMessage("Setting showLabels to " + args[3]);
						plugin.showLabels = args[3].equalsIgnoreCase("yes");
						// TODO: Better display the actual current value of this boolean
						//sender.sendMessage("Setting loyalty to " + Boolean.toString(plugin.loyalty));
					} else if(args[2].equalsIgnoreCase("teamLives")) {
						if(args.length <= 3) {
							sender.sendMessage("teamLives is set to " + plugin.teamLives);
							return true;
						}
						int lives = Integer.parseInt(args[3]);
						// Moved to command usage information (.displayUsage)
//						if(lives<0) {
//							sender.sendMessage("Argument must be equal or greater than 0. (Not " + args[3] + ")");
//							return true;
//						}
						sender.sendMessage("Setting teamLives to " + args[3]);
						plugin.teamLives = lives;
					} else if(args[2].equalsIgnoreCase("loyalty")) {
						if(args.length <= 3) {
							sender.sendMessage("loyalty is set to " + (plugin.loyalty?"YES":"NO"));
							return true;
						}
//						if(!args[3].equalsIgnoreCase("yes") && !args[3].equalsIgnoreCase("no")) {
//							sender.sendMessage("Argument must be 'yes' or 'no'. (Not " + args[3] + ")");
//							return true;
//						}
						sender.sendMessage("Setting loyalty to " + args[3]);
						plugin.loyalty = args[3].equalsIgnoreCase("yes");
					} else if(args[2].equalsIgnoreCase("teamHit")) {
						if(args.length <= 3) {
							sender.sendMessage("teamHit is set to " + (plugin.teamHit?"YES":"NO"));
							return true;
						}
//						if(!args[3].equalsIgnoreCase("yes") && !args[3].equalsIgnoreCase("no")) {
//							sender.sendMessage("Argument must be 'yes' or 'no'. (Not " + args[3] + ")");
//							return true;
//						}
						sender.sendMessage("Setting teamHit to " + args[3]);
						plugin.teamHit = args[3].equalsIgnoreCase("yes");
					} else {
						sender.sendMessage(cmd.getName() + ": No such teams command: " + args[2]);
					}
				} else {
					sender.sendMessage(cmd.getName() + ": No such setup command: " + args[1]);
				}

			} else if(args[0].equalsIgnoreCase("start")) {
				sender.sendMessage("Starting");
				plugin.startGame(sender);
			} else if(args[0].equalsIgnoreCase("stop")) {
				sender.sendMessage("Stopping");
				plugin.endGame();
			}
			return true;
		}
		return false;
	}
}
