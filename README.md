# bukkitTeamSurvival


What?
-----

This is a plugin for the [Minecraft]("http://minecraft.net") [Bukkit]("http://bukkit.org") server.

It creates a world with fixed border and teams of two players each.

The world is deleted afterwards to ensure that you always get new terrain, ores, animals and chances to win :wink:

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