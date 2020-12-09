package de.md5lukas.pointers.variants;

import de.md5lukas.pointers.Pointers;
import de.md5lukas.pointers.TargetData;
import de.md5lukas.pointers.config.ActionBarConfiguration;
import de.md5lukas.pointers.config.PointerConfiguration;
import lombok.val;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ActionBarPointer extends Pointer {

    @NotNull
    private final ActionBarConfiguration config;

    public ActionBarPointer(Pointers pointers) {
        super(pointers.getPointerConfiguration().getActionBar().getInterval());

        config = pointers.getPointerConfiguration().getActionBar();
    }

    @Override
    public void update(@NotNull Player player, @NotNull TargetData target) {
        val location = target.getLocation();
        if (player.getWorld().equals(location.getWorld())) {
            player.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    new TextComponent(
                            generateDirectionIndicator(
                                    config,
                                    deltaAngleToTarget(
                                            player.getLocation(),
                                            location
                                    )
                            )
                    )
            );
        } else {
            val result = config.getIncorrectWorldMessageFormatter().apply(player, location.getWorld());
            if (result != null) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(result));
            }
        }
    }

    public static boolean isEnabled(PointerConfiguration config) {
        return config.getActionBar().isEnabled();
    }

    private static String generateDirectionIndicator(ActionBarConfiguration config, double angle) {
        if (angle > config.getRange()) {
            return config.getIndicatorColor() + config.getLeftArrow() +
                    config.getNormalColor() +
                    repeatString(config.getSection(), config.getAmountOfSections())
                    + config.getRightArrow();
        }

        if (-angle > config.getRange()) {
            return config.getNormalColor() + config.getLeftArrow() +
                    repeatString(config.getSection(),
                            config.getAmountOfSections()) + config.getIndicatorColor()
                    + config.getRightArrow();
        }

        double percent = -(angle / config.getRange());

        int nthSection = (int) Math.round(((double) (config.getAmountOfSections() - 1) / 2) * percent);

        nthSection += Math.round((double) config.getAmountOfSections() / 2);

        return config.getNormalColor() + config.getLeftArrow() + repeatString(config.getSection(), nthSection - 1) + config
                .getIndicatorColor() + config.getSection()
                + config.getNormalColor() + repeatString(config.getSection(),
                config.getAmountOfSections() - nthSection) + config.getRightArrow();
    }

    /**
     * The returned values range from -180 to 180 degrees, where as negative numbers mean you look to much left and
     * positive numbers you look too much right
     *
     * @param location The location to calculate the angle from
     * @param target   The target when looked at the angle is 0
     * @return The delta angle
     */
    private static double deltaAngleToTarget(Location location, Location target) {
        double playerAngle = location.getYaw() + 90;
        while (playerAngle < 0)
            playerAngle += 360;
        double angle = playerAngle - Math.toDegrees(Math.atan2(location.getZ() - target.getZ(), location.getX() - target.getX())) + 180;
        while (angle > 360)
            angle -= 360;
        if (angle > 180)
            angle = -(360 - angle);
        return angle;
    }

    private static String repeatString(String string, int amount) {
        StringBuilder result = new StringBuilder(string.length() * amount);
        for (int i = 0; i < amount; i++) {
            result.append(string);
        }
        return result.toString();
    }
}
