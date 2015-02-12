package com.github.maride.bukkitTeamSurvival;

public class BtsMatchSettings {

	boolean	showLabels	= false;	// Team.setNameTagVisibility (true=ALWAYS; false=HIDE_FOR_OTHER_TEAMS)
	int		teamLives	= 0;		// Starting amount of lives per Team
	boolean	loyalty		= false;	// Team loss on member death (if 0 lives left)
	boolean	teamHit		= false;	// Team.setAllowFriendlyFire
	
	public BtsMatchSettings() {
	}
	
	public BtsMatchSettings(BtsMatchSettings toCopy) {
		this.showLabels	= toCopy.showLabels;
		this.teamLives	= toCopy.teamLives;
		this.loyalty	= toCopy.loyalty;
		this.teamHit	= toCopy.teamHit;
	}
}
