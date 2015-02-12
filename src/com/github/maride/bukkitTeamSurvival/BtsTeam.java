package com.github.maride.bukkitTeamSurvival;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Color;

public class BtsTeam {
	private String name;
	private Color color;
	private int lives;
	private Set<UUID> playerUUIDs;

	public BtsTeam(String name) {
		this.name = name;
		// TODO: Assign a random team color
	}
	
	public BtsTeam(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public Set<UUID> getPlayerUUIDs() {
		return playerUUIDs;
	}
	
	public void setPlayerUUIDs(Set<UUID> playerUUIDs) {
		this.playerUUIDs = playerUUIDs;
	}
	
	public boolean addPlayerUUID(UUID uuid) {
		if(playerUUIDs == null) {
			playerUUIDs = new HashSet<UUID>();
		}
		return playerUUIDs.add(uuid);
	}
	
	public boolean removePlayerUUID(UUID uuid) {
		if(playerUUIDs == null) {
			playerUUIDs = new HashSet<UUID>();
		}
		return playerUUIDs.remove(uuid);
	}
}
