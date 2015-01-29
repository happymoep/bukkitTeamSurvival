# bukkitTeamSurvival


What?
-----

This is a plugin for the [Minecraft](http://minecraft.net) [Bukkit](http://bukkit.org) server.

It creates a world with fixed border and teams of two players each.

The world is deleted afterwards to ensure that you always get new terrain, ores, animals and chances to win :wink:

Command structure
-----------------

*/ts*

- `setup` Setup commands
	- `team` Commands regarding **one** Team
		- `create <teamname>` Create team with name `teamname`
		- `delete <teamname>` Delete team with name `teamname`
		- `add <teamname> <player> [player2 [...]]` Add player(s) to `teamname`
		- `remove <teamname> <player> [player2 [...]]` Remove player(s) from `teamname`
		- `color <teamname> <color>` Set color `color` for `teamname` (see [this](minecraft.gamepedia.com/Formatting_codes) for colors)
		- `list <teamname>` List members of `teamname`
	- `teams` Commands regarding **all** Teams
		- `list` Lists all teams
		- `showLabels <yes|no>` Set the label visibility
		- `teamLives <amount>` Sets the amount of lives a team has before passing out
		- `loyalty <yes|no>` Sets whether a team drops out if **one** team member dies.
		- `teamHit <yes|no>` Sets friendly fire (players of the same team harm each other, eg. accidentally)
- `start` Start the Team Survival!
- `stop` Terminate Team Survival round.

Todo
----

- Automatic world generation
- Restrict players after join to gamemode 2 (Spectator)
- Inform players about:
	- Player count
	- Team setup
- On start, ...
	- Clear inventory
	- Clear hunger
	- Clear HP and XP
	- Spread teams over the map
	- Set gamemode to 0 (Survival)
	- Set up scoreboard
- On death of a player, ...
	- Clear inventory
	- Set gamemode to 2 (Spectator)
	- Allow teleportation to the lobby
	- Remove from scoreboard
- On team win, ...
	- Teleport all players to lobby
	- Set all gamemodes to 2 (Spectator)
	- Remove scoreboard
	- Fireworks. :fireworks:
