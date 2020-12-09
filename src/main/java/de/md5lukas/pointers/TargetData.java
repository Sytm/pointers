package de.md5lukas.pointers;


import lombok.*;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@RequiredArgsConstructor
public final class TargetData {

    @NotNull
    @NonNull
    private final Location location;

    @Nullable
    private BeaconColor beaconColor = null;

    @Nullable
    private Particle particle = null;
}
