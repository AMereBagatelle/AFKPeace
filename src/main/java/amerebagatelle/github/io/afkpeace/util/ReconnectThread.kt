package amerebagatelle.github.io.afkpeace.util

import amerebagatelle.github.io.afkpeace.AFKPeaceClient
import amerebagatelle.github.io.afkpeace.cancelReconnect
import amerebagatelle.github.io.afkpeace.config.AFKPeaceConfigManager
import amerebagatelle.github.io.afkpeace.finishReconnect
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ServerAddress
import net.minecraft.client.network.ServerInfo
import java.io.IOException
import java.net.Socket

class ReconnectThread(serverInfo: ServerInfo) : Thread() {
    private val serverAddress: ServerAddress = ServerAddress.parse(serverInfo.address)

    /**
     * Tries to connect to the server using a socket as many times as is set, and returns if it could connect
     */
    override fun run() {
        val client = MinecraftClient.getInstance()
        val timesToAttempt = AFKPeaceConfigManager.RECONNECT_ATTEMPT_NUMBER.value()
        for (i in 0 until timesToAttempt) {
            try {
                val secondsBetweenAttempts = AFKPeaceConfigManager.SECONDS_BETWEEN_RECONNECT_ATTEMPTS.value()
                sleep(secondsBetweenAttempts * 1000L)
                for (i1 in 0..9) {
                    pingServer()
                }
                synchronized(this) {
                    AFKPeaceClient.LOGGER.info("Reconnecting to server.")
                    client.execute { finishReconnect() }
                }
                return
            } catch (e: IOException) {
                AFKPeaceClient.LOGGER.debug("Attempt failed.  Reason: " + e.message + " Attempt #: " + i + 1)
            } catch (e: InterruptedException) {
                AFKPeaceClient.LOGGER.debug("Attempt failed.  Reason: " + e.message + " Attempt #: " + i + 1)
            }
        }
        client.execute { cancelReconnect() }
    }

    private fun pingServer() {
        val startTime = System.nanoTime()
        val connectionSocket = Socket(serverAddress.address, serverAddress.port)
        connectionSocket.close()
        val endTime = System.nanoTime()
        if (endTime - startTime > 2 * 1e+9) throw IOException("Ping was greater than five seconds, being " + (endTime - startTime) * 1e-9)
    }
}