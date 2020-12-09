package de.md5lukas.pointers.config.storage;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public final class InMemoryCompassLocationStorage implements CompassLocationStorage {

    private final Map<UUID, Location> lastLocations = new HashMap<>();

    @Override
    public void saveLastLocation(@NotNull Player player, @NotNull Location location) {
        lastLocations.put(player.getUniqueId(), location);
    }

    @Override
    public void loadLastLocation(@NotNull Player player, @NotNull Consumer<@Nullable Location> callback) {
        callback.accept(lastLocations.remove(player.getUniqueId()));
    }
}
