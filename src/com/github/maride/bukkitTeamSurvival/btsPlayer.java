package com.github.maride.bukkitTeamSurvival;

import com.github.maride.bukkitTeamSurvival.btsTeam;
import org.bukkit.entity.Player;

public class btsPlayer {
	btsTeam team;
	private String name;
	private Player entity;

	public btsPlayer(String playerName) {
		// this(Server.getPlayer(playerName));
	}
	
	public btsPlayer(String playerByName,btsTeam playerTeam) {
		// this(Server.getPlayer(playerName),playerTeam);
	}
	
	public btsPlayer(Player playerByEntity) {
		name = playerByEntity.getDisplayName();
		entity = playerByEntity;
	}
	
	public btsPlayer(Player playerByEntity,btsTeam playerTeam) {
		this(playerByEntity);
		team = playerTeam;
	}

	public String getName() {
		return name;
	}
}
