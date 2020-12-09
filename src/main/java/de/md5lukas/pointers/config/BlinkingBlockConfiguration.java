package de.md5lukas.pointers.config;

import lombok.*;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class BlinkingBlockConfiguration {

    @Setter
    private boolean enabled = false;
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
    @Setter
    private int interval = 40;

    public void setMinDistance(long minDistance) {
        this.minDistance = minDistance * minDistance;
    }

    public void setMaxDistance(long maxDistance) {
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
}
