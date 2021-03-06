# bukkitTeamSurvival

<p color='red'>Important:
This plugin is in the first development phase and the master branch is indev and not yet stable. Commits should compile but are not necessarily usable.
This will change as we reach v1.0</p>


What?
-----

This is a plugin for the [Minecraft](http://minecraft.net) [Bukkit](http://bukkit.org) server.

It creates a world with fixed border and teams of two players each.

The world is deleted afterwards to ensure that you always get new terrain, ores, animals and chances to win :wink:

Command structure
-----------------

`/bts` Commands for users

- `lobby`
	- `join` Teleport user to the lobby world
	- `leave` Teleport user to his position before last joining the lobby
- `team` Commands regarding **one** Team
	- `color <teamname>` Print out color of team `teamname`
	- `list <teamname>` List members of team `teamname`
- `settings` View settings for the next match (when in lobby) or current match (when in a match)


`/btss` Commands for admins (BukkitTeamSurvival Setup)

- `lobby`
	- `set [worldname]` Make this world the lobby or (if name provided) load world `worldname` or create it
- `team` Commands regarding **one** Team
	- `add <teamname> <player> [player2 [...]]` Add player(s) to `teamname`
	- `remove <teamname> <player> [player2 [...]]` Remove player(s) from `teamname`
	- `color <teamname> [color]` Set color `color` for `teamname` (see [this](http://minecraft.gamepedia.com/Formatting_codes) for colors)
- `settings` Commands regarding **all** Teams
	- `showLabels <yes|no>` Set the label visibility
	- `teamLives <amount>` Sets the amount of lives a team has before passing out
	- `loyalty <yes|no>` Sets whether a team drops out if **one** team member dies.
	- `teamHit <yes|no>` Sets friendly fire (players of the same team harm each other, eg. accidentally)
- `start` Start a match from the lobby
- `stop` Terminate a match (if issued from within the matches world)

Todo
----

- Automatic world generation
- Restrict players after join to gamemode 3 (Spectator)
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
	- Set gamemode to 3 (Spectator)
	- Allow teleportation to the lobby
	- Remove from scoreboard
- On team win, ...
	- Teleport all players to lobby
	- Set all gamemodes to 3 (Spectator)
	- Remove scoreboard
	- Fireworks. :fireworks:


Old command structure (for reference)
-----------------

*/bts*

- `setup` Setup commands
	- `team` Commands regarding **one** Team
		- `add <teamname> <player> [player2 [...]]` Add player(s) to `teamname`
		- `remove <teamname> <player> [player2 [...]]` Remove player(s) from `teamname`
		- `color <teamname> <color>` Set color `color` for `teamname` (see [this](http://minecraft.gamepedia.com/Formatting_codes) for colors)
		- `list <teamname>` List members of `teamname`
	- `teams` Commands regarding **all** Teams
		- `showLabels <yes|no>` Set the label visibility
		- `teamLives <amount>` Sets the amount of lives a team has before passing out
		- `loyalty <yes|no>` Sets whether a team drops out if **one** team member dies.
		- `teamHit <yes|no>` Sets friendly fire (players of the same team harm each other, eg. accidentally)
- `start` Start the Team Survival!
- `stop` Terminate Team Survival round.