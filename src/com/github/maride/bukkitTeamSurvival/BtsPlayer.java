package com.github.maride.bukkitTeamSurvival;

import com.github.maride.bukkitTeamSurvival.BtsTeam;
import org.bukkit.entity.Player;

public class BtsPlayer {
	BtsTeam team;
	private String name;
	private Player entity;

	public BtsPlayer(String playerName) {
		// this(Server.getPlayer(playerName));
	}
	
	public BtsPlayer(String playerByName,BtsTeam playerTeam) {
		// this(Server.getPlayer(playerName),playerTeam);
	}
	
	public BtsPlayer(Player playerByEntity) {
		name = playerByEntity.getDisplayName();
		entity = playerByEntity;
	}
	
	public BtsPlayer(Player playerByEntity,BtsTeam playerTeam) {
		this(playerByEntity);
		team = playerTeam;
	}

	public String getName() {
		return name;
	}
}
