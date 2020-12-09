package de.md5lukas.pointers.config;

import de.md5lukas.pointers.BeaconColor;
import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class BeaconConfiguration {

    @Setter
    private boolean enabled = false;
    /**
     * Min Value should not overlap with the distance range of the blinking block
     *
     * @return The squared minimum distance
     */
    private long minDistance = 30;
    /**
     * Default value is the view distance
     *
     * @return The squared maximum distance
     */
    private long maxDistance = Bukkit.getViewDistance() * 16;
    @NotNull
    @NonNull
    @Setter
    private BlockData baseBlock = Material.IRON_BLOCK.createBlockData();
    @Setter
    private int interval = 100;
    @NotNull
    @NonNull
    @Setter
    private BeaconColor defaultColor = BeaconColor.CLEAR;

    public void setMinDistance(long minDistance) {
        this.minDistance = minDistance * minDistance;
    }

    public void setMaxDistance(long maxDistance) {
        this.maxDistance = maxDistance * maxDistance;
    }

    public void setBaseBlockFromMaterial(@NotNull @NonNull Material baseBlock) {
        this.baseBlock = baseBlock.createBlockData();
    }
}
