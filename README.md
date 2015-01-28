# bukkitTeamSurvival


What?
-----

This is a plugin for the [Minecraft]("http://minecraft.net") [Bukkit]("http://bukkit.org") server.

It creates a world with fixed border and teams of two players each.

The world is deleted afterwards to ensure that you always get new terrain, ores, animals and chances to win :wink:

Command structure
-----------------

*/ts*

- `setup` Setup commands
	- `team` Commands regarding **one** Team
		- `add <Teamname>` Add Team with name `Teamname`
		- `remove <Teamname>` Remove Team with name `Teamname`
		- `put <Player> <Teamname>` Put Player named `Player` into `Teamname`
		- `pull <Player> <Teamname>` Pull Player named `Player` out of `Teamname`
		- `color {color} <Teamname>` Set color `color` for `Teamname` (see [this](minecraft.gamepedia.com/Formatting_codes) for colors)
		- `list <Teamname>` List members of `Teamname`
	- `teams` Commands regarding **all** Teams
		- `list` Lists all teams
		- `showLabels {bool}` Set the label visibility
		- `teamLives {int}` Sets the lives a team has before passing out
		- `loyality {bool}` Sets wether a team drops out if **one** team member dies.
		- `teamHit {bool}` Sets friendly fire (players of the same team harm each other, eg. accidentally)
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
