package amerebagatelle.github.io.afkpeace

import amerebagatelle.github.io.afkpeace.AFKManager.isAfk
import amerebagatelle.github.io.afkpeace.AFKManager.tickAfkStatus
import amerebagatelle.github.io.afkpeace.config.AFKPeaceConfigManager
import amerebagatelle.github.io.afkpeace.config.AFKPeaceConfigScreen
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.client.network.ServerInfo
import net.minecraft.client.option.KeyBind
import net.minecraft.client.resource.language.I18n
import net.minecraft.client.util.math.MatrixStack
import org.apache.logging.log4j.LogManager
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents
import org.quiltmc.qsl.networking.api.PacketSender
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents

class AFKPeaceClient : ClientModInitializer {
    override fun onInitializeClient(mod: ModContainer) {
        settingsKeybind = KeyBindingHelper.registerKeyBinding(KeyBind("afkpeace.keybind.settingsMenu", -1, "AFKPeace"))
        ClientTickEvents.END.register(ClientTickEvents.End { client: MinecraftClient ->
            if (settingsKeybind.wasPressed()) {
                client.setScreen(AFKPeaceConfigScreen(client.currentScreen))
            }
            if (AFKPeaceConfigManager.AUTO_AFK.value()) {
                tickAfkStatus()
            }
        })
        HudRenderCallback.EVENT.register(HudRenderCallback { matrices: MatrixStack?, tickDelta: Float ->
            if ((AFKPeaceConfigManager.DAMAGE_LOGOUT_ENABLED.value() || isAfk) && AFKPeaceConfigManager.FEATURES_ENABLED_INDICATOR.value()) {
                val textRenderer = MinecraftClient.getInstance().textRenderer
                textRenderer.draw(matrices, I18n.translate("afkpeace.hud.featuresEnabled"), 10f, 10f, 0xFFFFFF)
            }
        })
        ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { networkHandler: ClientPlayNetworkHandler?, packetSender: PacketSender?, client: MinecraftClient ->
            currentServerEntry = client.currentServerEntry
        })
        LOGGER.info("AFKPeace " + mod.metadata().version().raw() + " Initialized")
    }

    companion object {
        val LOGGER = LogManager.getLogger("AFKPeace")

        @Suppress("unused")
        val MODID = "afkpeace"
        lateinit var settingsKeybind: KeyBind

        @JvmField
        var currentServerEntry: ServerInfo? = null

        @JvmField
        var loginScreen: Screen? = null

        @JvmField
        var disabled = false
    }
}