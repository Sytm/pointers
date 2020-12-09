package de.md5lukas.pointers.config.storage;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface CompassLocationStorage {

    void saveLastLocation(@NotNull Player player, @NotNull Location location);

    void loadLastLocation(@NotNull Player player, @NotNull Consumer<@Nullable Location> callback);
}
