package amerebagatelle.github.io.afkpeace.config

import amerebagatelle.github.io.afkpeace.config.AFKPeaceConfigManager.setConfigValue
import dev.lambdaurora.spruceui.Position
import dev.lambdaurora.spruceui.background.Background
import dev.lambdaurora.spruceui.background.SimpleColorBackground
import dev.lambdaurora.spruceui.border.Border
import dev.lambdaurora.spruceui.border.EmptyBorder
import dev.lambdaurora.spruceui.option.SpruceIntegerInputOption
import dev.lambdaurora.spruceui.option.SpruceOption
import dev.lambdaurora.spruceui.option.SpruceToggleBooleanOption
import dev.lambdaurora.spruceui.screen.SpruceScreen
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text

class AFKPeaceConfigScreen(private val parent: Screen?) : SpruceScreen(Text.translatable("afkpeace.config.title")) {
    private val autoAfk: SpruceOption = SpruceToggleBooleanOption(
        "afkpeace.option.autoAfk", { AFKPeaceConfigManager.AUTO_AFK.value() },
        { newValue: Boolean -> setConfigValue(AFKPeaceConfigManager.AUTO_AFK, newValue) },
        Text.translatable("afkpeace.option.autoAfk.tooltip")
    )
    private val reconnectEnabled: SpruceOption = SpruceToggleBooleanOption(
        "afkpeace.option.reconnectEnabled", { AFKPeaceConfigManager.RECONNECT_ENABLED.value() },
        { newValue: Boolean -> setConfigValue(AFKPeaceConfigManager.RECONNECT_ENABLED, newValue) },
        Text.translatable("afkpeace.option.reconnectEnabled.tooltip")
    )
    private val damageLogoutEnabled: SpruceOption = SpruceToggleBooleanOption(
        "afkpeace.option.damageLogoutEnabled", { AFKPeaceConfigManager.DAMAGE_LOGOUT_ENABLED.value() },
        { newValue: Boolean -> setConfigValue(AFKPeaceConfigManager.DAMAGE_LOGOUT_ENABLED, newValue) },
        Text.translatable("afkpeace.option.damageLogoutEnabled.tooltip")
    )
    private val featuresEnabledIndicator: SpruceOption = SpruceToggleBooleanOption(
        "afkpeace.option.featuresEnabledIndicator", { AFKPeaceConfigManager.FEATURES_ENABLED_INDICATOR.value() },
        { newValue: Boolean -> setConfigValue(AFKPeaceConfigManager.FEATURES_ENABLED_INDICATOR, newValue) },
        Text.translatable("afkpeace.option.featuresEnabledIndicator.tooltip")
    )
    private val autoAfkTimerSeconds: SpruceOption = SpruceIntegerInputOption(
        "afkpeace.option.autoAfkTimer", { AFKPeaceConfigManager.AUTO_AFK_TIMER_SECONDS.value() },
        { newValue: Int -> setConfigValue(AFKPeaceConfigManager.AUTO_AFK_TIMER_SECONDS, newValue) },
        Text.translatable("afkpeace.option.autoAfkTimer.tooltip")
    )
    private val reconnectOnDamageLogout: SpruceOption = SpruceToggleBooleanOption(
        "afkpeace.option.reconnectOnDamageLogout", { AFKPeaceConfigManager.RECONNECT_ON_DAMAGE_LOGOUT.value() },
        { newValue: Boolean -> setConfigValue(AFKPeaceConfigManager.RECONNECT_ON_DAMAGE_LOGOUT, newValue) },
        Text.translatable("afkpeace.option.reconnectOnDamageLogout.tooltip")
    )
    private val secondsBetweenReconnectAttempts: SpruceOption = SpruceIntegerInputOption(
        "afkpeace.option.secondsBetweenReconnectAttempts",
        { AFKPeaceConfigManager.SECONDS_BETWEEN_RECONNECT_ATTEMPTS.value() },
        { newValue: Int -> setConfigValue(AFKPeaceConfigManager.SECONDS_BETWEEN_RECONNECT_ATTEMPTS, newValue) },
        Text.translatable("afkpeace.option.secondsBetweenReconnectAttempts.tooltip")
    )
    private val reconnectAttemptNumber: SpruceOption = SpruceIntegerInputOption(
        "afkpeace.option.reconnectAttemptNumber", { AFKPeaceConfigManager.RECONNECT_ATTEMPT_NUMBER.value() },
        { newValue: Int -> setConfigValue(AFKPeaceConfigManager.RECONNECT_ATTEMPT_NUMBER, newValue) },
        Text.translatable("afkpeace.option.reconnectAttemptNumber.tooltip")
    )
    private val damageLogoutTolerance: SpruceOption = SpruceIntegerInputOption(
        "afkpeace.option.damageLogoutTolerance", { AFKPeaceConfigManager.DAMAGE_LOGOUT_TOLERANCE.value() },
        { newValue: Int -> setConfigValue(AFKPeaceConfigManager.DAMAGE_LOGOUT_TOLERANCE, newValue) },
        Text.translatable("afkpeace.option.damageLogoutTolerance.tooltip")
    )

    override fun init() {
        super.init()
        addDrawableChild(
            SpruceButtonWidget(
                Position.of(2, 0),
                100,
                20,
                Text.translatable("afkpeace.config.back")
            ) {
                client!!.setScreen(
                    parent
                )
            })
        val options = SpruceOptionListWidget(Position.of(0, 20), width, height - 20)
        options.addSingleOptionEntry(autoAfk)
        options.addSingleOptionEntry(reconnectEnabled)
        options.addSingleOptionEntry(damageLogoutEnabled)
        options.addSingleOptionEntry(featuresEnabledIndicator)
        options.addSingleOptionEntry(autoAfkTimerSeconds)
        options.addSingleOptionEntry(reconnectOnDamageLogout)
        options.addSingleOptionEntry(secondsBetweenReconnectAttempts)
        options.addSingleOptionEntry(reconnectAttemptNumber)
        options.addSingleOptionEntry(damageLogoutTolerance)
        options.background = BACKGROUND
        options.border = WIDGET_BORDER
        addDrawableChild(options)
    }

    override fun renderTitle(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        drawCenteredText(matrices, textRenderer, title, width / 2, 8, 16777215)
    }

    companion object {
        private val BACKGROUND: Background = SimpleColorBackground(0, 0, 0, 135)
        private val WIDGET_BORDER: Border = EmptyBorder.EMPTY_BORDER
    }
}