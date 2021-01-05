# AFKPeace

The mod to give you peace of mind while AFK!

## Features

### Current Features:

- AFK Disconnect Protection:  Will try to reconnect on being disconnected.

- Logout on being attacked

- Configuration via GUI, accessed through ModMenu

### Future Features:

None planned!  If you would like to request something visit
the [issue tracker](https://github.com/AMereBagatelle/AFKPeace/issues).

## Configuration

### Client Settings

This mod is configured entirely through GUI when installed on the client, which can be accessed via
the [ModMenu](https://www.curseforge.com/minecraft/mc-mods/modmenu) mod.

- autoAFK: Controls whether your client will automatically turn on reconnecting and damage logout after a period of time
- autoAFKTimer: Controls the amount of time in seconds before the auto afk system activates
- reconnectEnabled: Whether you will reconnect to the server on disconnect
- damageLogoutEnabled: Whether you will disconnect from the server upon taking damage
- reconnectOnDamageLogout: Whether you will reconnect to the server upon logging out from the damage logout system
- secondsBetweenReconnectAttempts: The amount of time that will be waited in between reconnect attempts
- reconnectAttemptNumber: The amount of times the mod will attempt to reconnect to the server
- damageLogoutTolerance: The amount of damage in hitpoints (half-heart) before the damage logout system triggers

### Server Settings

When installed on the server, AFKPeace allows server owners to disable the mod on clients.

- disableAFKPeace: Whether clients that join with the mod will have the mod disabled
