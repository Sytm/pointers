package de.md5lukas.pointers.config;

import de.md5lukas.pointers.BeaconColor;
import lombok.*;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class BlinkingBlockConfiguration {

    private boolean enabled = false;
    private int interval = 40;
    /**
     * @return The squared minimum distance
     */
    private long minDistance = 5;
    /**
     * The value should not overlap with the distance range of the beacon
     *
     * @return The squared maximum distance
     */
    private long maxDistance = 30;
    @NotNull
    private List<@NotNull BlockData> blockDataSequence = Collections.singletonList(Material.BEACON.createBlockData());

    public void setInterval(int interval) {
        if (interval <= 0)
            throw new IllegalArgumentException("The interval cannot be less or equal to zero (" + interval + ")");
        this.interval = interval;
    }

    public void setMinDistance(long minDistance) {
        if (minDistance <= 0)
            throw new IllegalArgumentException("The minDistance cannot be less or equal to zero (" + minDistance + ")");
        this.minDistance = minDistance * minDistance;
    }

    public void setMaxDistance(long maxDistance) {
        if (maxDistance <= 0)
            throw new IllegalArgumentException("The maxDistance cannot be less or equal to zero (" + maxDistance + ")");
        this.maxDistance = maxDistance * maxDistance;
    }

    public void setBlockSequence(@NotNull @NonNull Collection<@NotNull Material> materials) {
        List<@NotNull BlockData> newSequence = new ArrayList<>();
        for (val material : materials) {
            if (!material.isBlock()) {
                throw new IllegalArgumentException("One of the provided materials for the block sequence is not a block");
            }
            newSequence.add(material.createBlockData());
        }
        blockDataSequence = newSequence;
    }

    void loadFromConfiguration(ConfigurationSection cfg) throws InvalidConfigurationException {
        try {
            setEnabled(cfg.getBoolean("enabled"));

            setInterval(cfg.getInt("interval"));

            setMinDistance(cfg.getLong("minDistance"));

            setMaxDistance(cfg.getLong("minDistance"));

            val blockSequenceNames = cfg.getStringList("blockSequence");
            if (blockSequenceNames.isEmpty())
                throw new IllegalArgumentException("The blockSequence must not be empty");

            List<@NotNull BlockData> newSequence = new ArrayList<>(blockSequenceNames.size());
            for (val blockName : blockSequenceNames) {
                val material = Material.matchMaterial(blockName);
                if (material == null)
                    throw new IllegalArgumentException("The material '" + blockName + "' used in the blockSequence could not be found");
                if (!material.isBlock())
                    throw new IllegalArgumentException("The material '" + blockName + "' is not a block");
                newSequence.add(material.createBlockData());
            }
            blockDataSequence = newSequence;
        } catch (Exception e) {
            throw new InvalidConfigurationException("Invalid configuration in the blinking block pointer configuration", e);
        }
    }
}
