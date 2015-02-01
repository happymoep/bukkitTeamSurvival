package com.github.maride.bukkitTeamSurvival;

import com.github.maride.bukkitTeamSurvival.BTSTeam;
import org.bukkit.entity.Player;

public class BTSPlayer {
	BTSTeam team;
	private String name;
	private Player entity;

	public BTSPlayer(String playerName) {
		// this(Server.getPlayer(playerName));
	}
	
	public BTSPlayer(String playerByName,BTSTeam playerTeam) {
		// this(Server.getPlayer(playerName),playerTeam);
	}
	
	public BTSPlayer(Player playerByEntity) {
		name = playerByEntity.getDisplayName();
		entity = playerByEntity;
	}
	
	public BTSPlayer(Player playerByEntity,BTSTeam playerTeam) {
		this(playerByEntity);
		team = playerTeam;
	}

	public String getName() {
		return name;
	}
}
