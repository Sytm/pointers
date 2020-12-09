package de.md5lukas.pointers.variants;

import de.md5lukas.pointers.Pointers;
import de.md5lukas.pointers.TargetData;
import de.md5lukas.pointers.config.CompassConfiguration;
import de.md5lukas.pointers.config.PointerConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class CompassPointer extends Pointer {

    @NotNull
    private final CompassConfiguration config;

    public CompassPointer(Pointers pointers) {
        super(0);

        config = pointers.getPointerConfiguration().getCompass();
    }

    @Override
    public void show(@NotNull Player player, @NotNull TargetData target) {
        if (config.getResetTarget() == null) {
            config.getLocationStorage().saveLastLocation(player, player.getCompassTarget());
        }
        player.setCompassTarget(target.getLocation());
    }

    @Override
    public void hide(@NotNull Player player, @NotNull TargetData target) {
        if (config.getResetTarget() != null) {
            player.setCompassTarget(config.getResetTarget());
        } else {
            config.getLocationStorage().loadLastLocation(player, oldLocation -> {
                if (oldLocation != null) {
                    player.setCompassTarget(oldLocation);
                }
            });
        }
    }

    public static boolean isEnabled(PointerConfiguration config) {
        return config.getCompass().isEnabled();
    }
}
