package com.github.maride.bukkitTeamSurvival;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.scoreboard.Scoreboard;

public class BtsMatch {
	private	World			world;
	private	Set<BtsTeam>	teams;
	private Scoreboard		board;
	
	private	BtsMatchSettings	matchSettings;
	
	public BtsMatch() {
	}
	
	public BtsMatch(World world, Set<BtsTeam> teams, Scoreboard board, BtsMatchSettings matchSettings) {
		this.world = world;
		this.teams = teams;
		this.board = board;
		this.matchSettings = matchSettings;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public Set<BtsTeam> getTeams() {
		return teams;
	}
	
	public void setTeams(Set<BtsTeam> teams) {
		this.teams = teams;
	}
	
	public Scoreboard getBoard() {
		return board;
	}
	
	public void setBoard(Scoreboard board) {
		this.board = board;
	}
	
	public BtsMatchSettings getMatchSettings() {
		if(matchSettings == null) {
			matchSettings = new BtsMatchSettings();
		}
		return matchSettings;
	}

	public void setMatchSettings(BtsMatchSettings matchSettings) {
		this.matchSettings = matchSettings;
	}

	/**
	 * @param team - team to be added to this BtsMatch
	 * @return true if this BtsMatch did not already contain the specified team 
	 */
	public boolean addTeam(BtsTeam team) {
		if(teams == null) {
			teams = new HashSet<BtsTeam>();
		}
		return teams.add(team);
	}
	
	/**
	 * (This BtsMatch will not contain the element once the call returns.)
	 * @param team - team to be removed from this BtsMatch, if present
	 * @return true if this BtsMatch contained the specified team 
	 */
	public boolean removePlayerUUID(BtsTeam team) {
		if(teams == null) {
			teams = new HashSet<BtsTeam>();
		}
		return teams.remove(team);
	}
	
	public void stop() {
		/* TODO:
		 *  Clear inventory
		 *  Set gamemode to DefaultGamemode for lobby world ?!?!?
		 *  Teleport to lobby
		 */
	}
	
	public void freeWorld() {
		this.world = null; // Better not keep world references
	}
}
