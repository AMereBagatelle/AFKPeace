package amerebagatelle.github.io.afkpeace

import amerebagatelle.github.io.afkpeace.config.AFKPeaceConfigManager
import amerebagatelle.github.io.afkpeace.util.ReconnectThread
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ConnectScreen
import net.minecraft.client.gui.screen.DisconnectedScreen
import net.minecraft.client.gui.screen.TitleScreen
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen
import net.minecraft.client.network.ServerAddress
import net.minecraft.client.network.ServerInfo
import net.minecraft.text.Text

@JvmField
var isDisconnecting: Boolean = false

var reconnectThread: ReconnectThread? = null

fun startReconnect(target: ServerInfo) {
    val client = MinecraftClient.getInstance()
    client.disconnect()
    reconnectThread = ReconnectThread(target)
    reconnectThread!!.start()
    client.setScreen(
        DisconnectedScreen(
            MultiplayerScreen(TitleScreen()),
            Text.literal("AFKPeaceReconnection"),
            Text.translatable("afkpeace.reconnect.reason")
        )
    )
}

fun finishReconnect() = connectToServer(AFKPeaceClient.currentServerEntry!!)

fun cancelReconnect() {
    try {
        reconnectThread!!.join()
    } catch (ignored: InterruptedException) {
    }
    AFKPeaceClient.LOGGER.debug("Reconnecting cancelled.")
    MinecraftClient.getInstance().setScreen(
        DisconnectedScreen(
            MultiplayerScreen(TitleScreen()),
            Text.literal("AFKPeaceDisconnect"),
            Text.translatable("afkpeace.reconnectfail")
        )
    )
}

fun connectToServer(target: ServerInfo) =
    ConnectScreen.connect(
        MultiplayerScreen(TitleScreen()),
        MinecraftClient.getInstance(),
        ServerAddress.parse(target.address),
        target,
        false
    )

fun disconnectFromServer(reason: Text) {
    if (AFKPeaceConfigManager.RECONNECT_ON_DAMAGE_LOGOUT.value()) {
        if (AFKPeaceClient.currentServerEntry != null) startReconnect(AFKPeaceClient.currentServerEntry!!)
        return
    }

    isDisconnecting = true
    val client = MinecraftClient.getInstance()
    client.networkHandler!!.connection.disconnect(reason)
    client.disconnect()
    client.setScreen(
        DisconnectedScreen(
            MultiplayerScreen(TitleScreen()),
            Text.literal("AFKPeaceDisconnect"),
            reason
        )
    )
}