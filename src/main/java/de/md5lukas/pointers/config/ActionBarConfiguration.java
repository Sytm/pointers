package de.md5lukas.pointers.config;

import lombok.*;
import org.bukkit.ChatColor;
import org.bukkit.World;
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
     *
     * The returned string is then sent to the player. If no message is provided none will be sent.
     *
     */
    @NotNull
    @NonNull
    private BiFunction<@NotNull Player, @NotNull World, @Nullable String> incorrectWorldMessageFormatter = ActionBarConfiguration::defaultIncorrectWorldMessageFormatter;

    public void setIndicatorColor(@NotNull @NonNull String indicatorColor) {
        this.indicatorColor = ChatColor.translateAlternateColorCodes('&', indicatorColor);
    }

    public void setNormalColor(@NotNull @NonNull String normalColor) {
        this.normalColor = ChatColor.translateAlternateColorCodes('&', normalColor);
    }

    private static String defaultIncorrectWorldMessageFormatter(Player player, World world) {
        return "&cYou are in the world &e" + player.getWorld().getName() + "&c, but your target is in the world &e" + world.getName();
    }
}
