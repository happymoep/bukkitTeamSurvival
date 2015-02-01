package com.github.maride.bukkitTeamSurvival;

import com.github.maride.bukkitTeamSurvival.BTSPlayer;

public class BTSTeam {
	BTSPlayer[] members;
	
	public void addPlayer(BTSPlayer thisPlayer) {
		if(thisPlayer.team != null) {
			// already got a team
			thisPlayer.team.removePlayer(thisPlayer);
			thisPlayer.team = this;
		}
		// add to members
	}
	
	public void removePlayer(BTSPlayer thisPlayer) {
		// remove from members
	}
}
