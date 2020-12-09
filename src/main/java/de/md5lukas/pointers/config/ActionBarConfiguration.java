package de.md5lukas.pointers.config;

import lombok.*;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class ActionBarConfiguration {

    private boolean enabled = false;
    private int interval = 20;
    @NotNull
    private String indicatorColor = "&4&l";
    @NotNull
    private String normalColor = "&7&l";
    @NotNull
    @NonNull
    private String section = "⬛";
    @NotNull
    @NonNull
    private String leftArrow = "<-";
    @NotNull
    @NonNull
    private String rightArrow = "->";
    private int amountOfSections = 35;
    private int range = 70;
    /**
     * The provided player is the player that should receive the message.<br>
     * The provided world is the world where the actual target is located.<br>
     * <br>
     * <p>
     * The returned string is then sent to the player. If no message is provided none will be sent.
     */
    @NotNull
    @NonNull
    private BiFunction<@NotNull Player, @NotNull World, @Nullable String> incorrectWorldMessageFormatter = ActionBarConfiguration::defaultIncorrectWorldMessageFormatter;

    public void setInterval(int interval) {
        if (interval <= 0)
            throw new IllegalArgumentException("The interval cannot be less or equal to zero (" + interval + ")");
        this.interval = interval;
    }

    public void setIndicatorColor(@NotNull @NonNull String indicatorColor) {
        this.indicatorColor = ChatColor.translateAlternateColorCodes('&', indicatorColor);
    }

    public void setNormalColor(@NotNull @NonNull String normalColor) {
        this.normalColor = ChatColor.translateAlternateColorCodes('&', normalColor);
    }

    private static String defaultIncorrectWorldMessageFormatter(Player player, World world) {
        return "&cYou are in the world &e" + player.getWorld().getName() + "&c, but your target is in the world &e" + world.getName();
    }

    public void setAmountOfSections(int amountOfSections) {
        if (amountOfSections <= 0)
            throw new IllegalArgumentException("The amountOfSections cannot be less or equal to zero (" + amountOfSections + ")");
        this.amountOfSections = amountOfSections;
    }

    public void setRange(int range) {
        if (range <= 0)
            throw new IllegalArgumentException("The range cannot be less or equal to zero (" + range + ")");
        this.range = range;
    }

    @SuppressWarnings("ConstantConditions")
        // Suppressing this so the exceptions can be properly thrown
    void loadFromConfiguration(ConfigurationSection cfg) throws InvalidConfigurationException {
        try {
            setEnabled(cfg.getBoolean("enabled"));

            setInterval(cfg.getInt("interval"));

            setIndicatorColor(cfg.getString("indicatorColor"));

            setNormalColor(cfg.getString("normalColor"));

            setSection(cfg.getString("section"));

            setLeftArrow(cfg.getString("leftArrow"));

            setRightArrow(cfg.getString("rightArrow"));

            setAmountOfSections(cfg.getInt("amountOfSections"));

            setRange(cfg.getInt("range"));
        } catch (Exception e) {
            throw new InvalidConfigurationException("Invalid configuration in the action bar pointer configuration", e);
        }
    }
}
