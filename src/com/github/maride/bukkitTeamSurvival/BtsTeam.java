package com.github.maride.bukkitTeamSurvival;

import com.github.maride.bukkitTeamSurvival.BtsPlayer;

public class BtsTeam {
	BtsPlayer[] members;
	
	public void addPlayer(BtsPlayer thisPlayer) {
		if(thisPlayer.team != null) {
			// already got a team
			thisPlayer.team.removePlayer(thisPlayer);
			thisPlayer.team = this;
		}
		// add to members
	}
	
	public void removePlayer(BtsPlayer thisPlayer) {
		// remove from members
	}
}
