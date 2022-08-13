package amerebagatelle.github.io.afkpeace.config;

import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.background.Background;
import dev.lambdaurora.spruceui.background.SimpleColorBackground;
import dev.lambdaurora.spruceui.border.Border;
import dev.lambdaurora.spruceui.border.EmptyBorder;
import dev.lambdaurora.spruceui.option.SpruceIntegerInputOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.option.SpruceToggleBooleanOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import javax.annotation.Nullable;
import java.util.Objects;

public class AFKPeaceConfigScreen extends SpruceScreen {
    private static final Background BACKGROUND = new SimpleColorBackground(0, 0, 0, 135);
    private static final Border WIDGET_BORDER = EmptyBorder.EMPTY_BORDER;

    private final Screen parent;

    private final SpruceOption autoAfk;
    private final SpruceOption reconnectEnabled;
    private final SpruceOption damageLogoutEnabled;
    private final SpruceOption featuresEnabledIndicator;

    private final SpruceOption autoAfkTimerSeconds;
    private final SpruceOption reconnectOnDamageLogout;
    private final SpruceOption secondsBetweenReconnectAttempts;
    private final SpruceOption reconnectAttemptNumber;
    private final SpruceOption damageLogoutTolerance;

    public AFKPeaceConfigScreen(@Nullable Screen parent) {
        super(Text.translatable("afkpeace.config.title"));
        this.parent = parent;

        autoAfk = new SpruceToggleBooleanOption("afkpeace.option.autoAfk",
                AFKPeaceConfigManager.AUTO_AFK::value,
                newValue -> AFKPeaceConfigManager.setConfigValue(AFKPeaceConfigManager.AUTO_AFK, newValue),
                Text.translatable("afkpeace.option.autoAfk.tooltip")
        );
        reconnectEnabled = new SpruceToggleBooleanOption("afkpeace.option.reconnectEnabled",
                AFKPeaceConfigManager.RECONNECT_ENABLED::value,
                newValue -> AFKPeaceConfigManager.setConfigValue(AFKPeaceConfigManager.RECONNECT_ENABLED, newValue),
                Text.translatable("afkpeace.option.reconnectEnabled.tooltip")
        );
        damageLogoutEnabled = new SpruceToggleBooleanOption("afkpeace.option.damageLogoutEnabled",
                AFKPeaceConfigManager.DAMAGE_LOGOUT_ENABLED::value,
                newValue -> AFKPeaceConfigManager.setConfigValue(AFKPeaceConfigManager.DAMAGE_LOGOUT_ENABLED, newValue),
                Text.translatable("afkpeace.option.damageLogoutEnabled.tooltip")
        );
        featuresEnabledIndicator = new SpruceToggleBooleanOption("afkpeace.option.featuresEnabledIndicator",
                AFKPeaceConfigManager.FEATURES_ENABLED_INDICATOR::value,
                newValue -> AFKPeaceConfigManager.setConfigValue(AFKPeaceConfigManager.FEATURES_ENABLED_INDICATOR, newValue),
                Text.translatable("afkpeace.option.featuresEnabledIndicator.tooltip")
        );

        autoAfkTimerSeconds = new SpruceIntegerInputOption("afkpeace.option.autoAfkTimerSeconds",
                AFKPeaceConfigManager.AUTO_AFK_TIMER_SECONDS::value,
                newValue -> AFKPeaceConfigManager.setConfigValue(AFKPeaceConfigManager.AUTO_AFK_TIMER_SECONDS, newValue),
                Text.translatable("afkpeace.option.autoAfkTimerSeconds.tooltip")
        );
        reconnectOnDamageLogout = new SpruceToggleBooleanOption("afkpeace.option.reconnectOnDamageLogout",
                AFKPeaceConfigManager.RECONNECT_ON_DAMAGE_LOGOUT::value,
                newValue -> AFKPeaceConfigManager.setConfigValue(AFKPeaceConfigManager.RECONNECT_ON_DAMAGE_LOGOUT, newValue),
                Text.translatable("afkpeace.option.reconnectOnDamageLogout.tooltip")
        );
        secondsBetweenReconnectAttempts = new SpruceIntegerInputOption("afkpeace.option.secondsBetweenReconnectAttempts",
                AFKPeaceConfigManager.SECONDS_BETWEEN_RECONNECT_ATTEMPTS::value,
                newValue -> AFKPeaceConfigManager.setConfigValue(AFKPeaceConfigManager.SECONDS_BETWEEN_RECONNECT_ATTEMPTS, newValue),
                Text.translatable("afkpeace.option.secondsBetweenReconnectAttempts.tooltip")
        );
        reconnectAttemptNumber = new SpruceIntegerInputOption("afkpeace.option.autoAfkTimerSeconds",
                AFKPeaceConfigManager.RECONNECT_ATTEMPT_NUMBER::value,
                newValue -> AFKPeaceConfigManager.setConfigValue(AFKPeaceConfigManager.RECONNECT_ATTEMPT_NUMBER, newValue),
                Text.translatable("afkpeace.option.autoAfkTimerSeconds.tooltip")
        );
        damageLogoutTolerance = new SpruceIntegerInputOption("afkpeace.option.autoAfkTimerSeconds",
                AFKPeaceConfigManager.DAMAGE_LOGOUT_TOLERANCE::value,
                newValue -> AFKPeaceConfigManager.setConfigValue(AFKPeaceConfigManager.DAMAGE_LOGOUT_TOLERANCE, newValue),
                Text.translatable("afkpeace.option.autoAfkTimerSeconds.tooltip")
        );
    }

    @Override
    protected void init() {
        super.init();

        this.addDrawableChild(new SpruceButtonWidget(Position.of(2, 0), 100, 20, Text.translatable("afkpeace.config.back"), (button) -> Objects.requireNonNull(client).setScreen(parent)));

        SpruceOptionListWidget options = new SpruceOptionListWidget(Position.of(0, 20), this.width, this.height - 20);

        options.addSingleOptionEntry(autoAfk);
        options.addSingleOptionEntry(reconnectEnabled);
        options.addSingleOptionEntry(damageLogoutEnabled);
        options.addSingleOptionEntry(featuresEnabledIndicator);
        options.addSingleOptionEntry(autoAfkTimerSeconds);
        options.addSingleOptionEntry(reconnectOnDamageLogout);
        options.addSingleOptionEntry(secondsBetweenReconnectAttempts);
        options.addSingleOptionEntry(reconnectAttemptNumber);
        options.addSingleOptionEntry(damageLogoutTolerance);

        options.setBackground(BACKGROUND);
        options.setBorder(WIDGET_BORDER);

        this.addDrawableChild(options);
    }

    @Override
    public void renderTitle(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
    }
}
