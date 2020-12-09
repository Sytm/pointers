package de.md5lukas.pointers.config;

import lombok.*;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class ParticleConfiguration {

    private boolean enabled = false;
    private int interval = 20;
    private double heightOffset = 0;
    private boolean showVerticalDirection = false;
    private int amount = 10;
    private double length = 1;
    @NotNull
    @NonNull
    private Particle particle = Particle.FLAME;
}
