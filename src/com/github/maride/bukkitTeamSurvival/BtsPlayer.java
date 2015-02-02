package com.github.maride.bukkitTeamSurvival;

import com.github.maride.bukkitTeamSurvival.BtsTeam;
import org.bukkit.entity.Player;

public class BtsPlayer {

	private	String	name;
	private Player	entity;
	private	BtsTeam	team;

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
	
	public void setName(String name) {
		this.name = name;
	}

	public Player getEntity() {
		return entity;
	}

	public void setEntity(Player entity) {
		this.entity = entity;
	}

	public BtsTeam getTeam() {
		return team;
	}

	public void setTeam(BtsTeam team) {
		this.team = team;
	}

}
