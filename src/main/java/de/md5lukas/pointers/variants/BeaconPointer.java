package de.md5lukas.pointers.variants;

import de.md5lukas.pointers.Pointers;
import de.md5lukas.pointers.TargetData;
import de.md5lukas.pointers.config.BeaconConfiguration;
import de.md5lukas.pointers.config.PointerConfiguration;
import lombok.val;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class BeaconPointer extends Pointer {

    @NotNull
    private static final BlockData BEACON_BLOCK_DATA = Material.BEACON.createBlockData();

    @NotNull
    private final BeaconConfiguration config;

    @NotNull
    private final Map<@NotNull UUID, @NotNull Location> activeBeacons = new HashMap<>();

    public BeaconPointer(Pointers pointers) {
        super(pointers.getPointerConfiguration().getBeacon().getInterval());

        config = pointers.getPointerConfiguration().getBeacon();
    }

    @Override
    public void update(@NotNull Player player, @NotNull TargetData target) {
        val location = target.getLocation();
        if (player.getWorld().equals(location.getWorld())) {
            val distance = player.getLocation().distanceSquared(location);

            if (distance >= config.getMinDistance() && distance < config.getMaxDistance()) {
                val beaconBase = location.getWorld().getHighestBlockAt(location).getLocation();

                val lastBeaconPosition = activeBeacons.get(player.getUniqueId());
                if (lastBeaconPosition != null && !blockEquals(beaconBase, lastBeaconPosition)) {
                    hide(player, target, lastBeaconPosition);
                }

                activeBeacons.put(player.getUniqueId(), beaconBase);

                sendBeacon(player, beaconBase, target, true);
            } else {
                hide(player, target);
            }
        }
    }

    @Override
    public void hide(@NotNull Player player, @NotNull TargetData target) {
        // Using this because theoretically someone could remove the highest block and
        // when recalculating the position of the beacon we would not completely remove it
        val lastBeaconPosition = activeBeacons.remove(player.getUniqueId());

        hide(player, target, lastBeaconPosition);
    }

    private void hide(@NotNull Player player, @NotNull TargetData target, @Nullable Location lastBeaconPosition) {
        if (lastBeaconPosition != null && player.getWorld().equals(lastBeaconPosition.getWorld())) {
            sendBeacon(player, lastBeaconPosition, target, false);
        }
    }

    public static boolean isEnabled(PointerConfiguration config) {
        return config.getBeacon().isEnabled();
    }

    private void sendBeacon(@NotNull Player player, @NotNull Location location, @NotNull TargetData targetData, boolean create) {
        val copy = location.clone();

        // Create or remove 3x3 base platform
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                copy.add(x, 0, z);
                if (create) {
                    player.sendBlockChange(copy, config.getBaseBlock());
                } else {
                    player.sendBlockChange(copy, copy.getBlock().getBlockData());
                }
                copy.subtract(x, 0, z);
            }
        }

        // Add one to place or remove the beacon
        copy.add(0, 1, 0);
        if (create) {
            player.sendBlockChange(copy, BEACON_BLOCK_DATA);
            // Add one again to place the coloring block
            copy.add(0, 1, 0);
            BlockData blockColor = config.getDefaultColor().getBlockData();
            if (targetData.getBeaconColor() != null) {
                blockColor = targetData.getBeaconColor().getBlockData();
            }
            player.sendBlockChange(copy, blockColor);
        } else {
            player.sendBlockChange(copy, copy.getBlock().getBlockData());
            // Add one again to remove the coloring block
            copy.add(0, 1, 0);
            player.sendBlockChange(copy, copy.getBlock().getBlockData());
        }
    }

    private static boolean blockEquals(Location loc1, Location loc2) {
        return loc1.getBlockX() == loc2.getBlockX() && loc1.getBlockY() == loc2.getBlockY() && loc1.getBlockZ() == loc2.getBlockZ();
    }
}
