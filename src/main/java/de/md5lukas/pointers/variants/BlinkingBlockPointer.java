package de.md5lukas.pointers.variants;

import de.md5lukas.pointers.Pointers;
import de.md5lukas.pointers.TargetData;
import de.md5lukas.pointers.config.BlinkingBlockConfiguration;
import de.md5lukas.pointers.config.PointerConfiguration;
import lombok.val;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class BlinkingBlockPointer extends Pointer {

    @NotNull
    private final BlinkingBlockConfiguration config;

    @NotNull
    private final Map<@NotNull UUID, @NotNull Integer> counters = new HashMap<>();

    public BlinkingBlockPointer(Pointers pointers) {
        super(pointers.getPointerConfiguration().getBlinkingBlock().getInterval());

        config = pointers.getPointerConfiguration().getBlinkingBlock();
    }

    @Override
    public void update(@NotNull Player player, @NotNull TargetData target) {
        val location = target.getLocation();
        if (player.getWorld().equals(location.getWorld())) {
            val distance = player.getLocation().distanceSquared(location);
            if (distance >= config.getMinDistance() && distance < config.getMaxDistance()) {
                val currentCounter = counters.compute(player.getUniqueId(), (uuid, count) -> {
                    if (count == null) {
                        return 0;
                    } else {
                        return (count + 1) % config.getBlockDataSequence().size();
                    }
                });
                player.sendBlockChange(location, config.getBlockDataSequence().get(currentCounter));
            } else {
                hide(player, target);
            }
        } else {
            counters.remove(player.getUniqueId());
        }
    }

    @Override
    public void hide(@NotNull Player player, @NotNull TargetData target) {
        val location = target.getLocation();
        counters.remove(player.getUniqueId());
        if (player.getWorld().equals(location.getWorld())) {
            player.sendBlockChange(location, location.getBlock().getBlockData());
        }
    }

    public static boolean isEnabled(PointerConfiguration config) {
        return config.getBlinkingBlock().isEnabled();
    }
}
