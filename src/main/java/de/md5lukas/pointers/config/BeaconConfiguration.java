package de.md5lukas.pointers.config;

import de.md5lukas.pointers.BeaconColor;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class BeaconConfiguration {

    private boolean enabled = false;
    private int interval = 100;
    /**
     * Min Value should not overlap with the distance range of the blinking block
     *
     * @return The squared minimum distance
     */
    @SuppressWarnings("JavaDoc")
    private long minDistance = 30;
    /**
     * Default value is the view distance
     *
     * @return The squared maximum distance
     */
    @SuppressWarnings("JavaDoc")
    private long maxDistance = Bukkit.getViewDistance() * 16;
    @NotNull
    @NonNull
    private BlockData baseBlock = Material.IRON_BLOCK.createBlockData();
    @NotNull
    @NonNull
    private BeaconColor defaultColor = BeaconColor.CLEAR;

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

    public void setBaseBlockFromMaterial(@NotNull @NonNull Material baseBlock) {
        this.baseBlock = baseBlock.createBlockData();
    }

    @SuppressWarnings("ConstantConditions") // Suppressing this so the exceptions can be properly thrown
    void loadFromConfiguration(ConfigurationSection cfg) throws InvalidConfigurationException {
        try {
            setEnabled(cfg.getBoolean("enabled"));

            setInterval(cfg.getInt("interval"));

            setMinDistance(cfg.getLong("minDistance"));

            setMaxDistance(cfg.getLong("minDistance"));

            val baseBlockMaterial = cfg.getString("baseBlock");
            if (baseBlockMaterial == null)
                throw new IllegalArgumentException("The material for the base block must be provided");
            setBaseBlockFromMaterial(Material.matchMaterial(baseBlockMaterial));

            val defaultColorName = cfg.getString("defaultColor");
            if (defaultColorName == null)
                throw new IllegalArgumentException("The default color must be provided");
            setDefaultColor(BeaconColor.valueOf(defaultColorName));
        } catch (Exception e) {
            throw new InvalidConfigurationException("Invalid configuration in the beacon pointer configuration", e);
        }
    }
}
