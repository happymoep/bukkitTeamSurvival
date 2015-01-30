package com.github.maride.bukkitTeamSurvival;

import com.github.maride.bukkitTeamSurvival.btsPlayer;

public class btsTeam {
	btsPlayer[] members;
	
	public void addPlayer(btsPlayer thisPlayer) {
		if(thisPlayer.team != null) {
			// already got a team
			thisPlayer.team.removePlayer(thisPlayer);
			thisPlayer.team = this;
		}
		// add to members
	}
	
	public void removePlayer(btsPlayer thisPlayer) {
		// remove from members
	}
}
