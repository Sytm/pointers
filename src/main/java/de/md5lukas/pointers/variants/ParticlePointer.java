package de.md5lukas.pointers.variants;

import de.md5lukas.pointers.Pointers;
import de.md5lukas.pointers.TargetData;
import de.md5lukas.pointers.config.ParticleConfiguration;
import de.md5lukas.pointers.config.PointerConfiguration;
import lombok.val;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ParticlePointer extends Pointer {

    @NotNull
    private final ParticleConfiguration config;

    public ParticlePointer(@NotNull Pointers pointers) {
        super(pointers.getPointerConfiguration().getParticle().getInterval());

        config = pointers.getPointerConfiguration().getParticle();
    }

    @Override
    public void update(@NotNull Player player, @NotNull TargetData target) {
        val location = target.getLocation();
        if (player.getWorld().equals(location.getWorld())) {
            val pLoc = player.getLocation();
            val dir = location.toVector().subtract(player.getLocation().toVector())
                    .normalize()
                    .multiply(config.getLength());

            val amount = config.getAmount();

            dir.setX(dir.getX() / amount);
            dir.setY(dir.getY() / amount);
            dir.setY(dir.getZ() / amount);

            Particle particle = config.getParticle();

            if (target.getParticle() != null) {
                particle = target.getParticle();
            }

            for (int i = 0; i < amount; i++) {
                double y = config.getHeightOffset();
                if (config.isShowVerticalDirection()) {
                    y += dir.getY() * i;
                }

                player.spawnParticle(
                        particle,
                        pLoc.getX() + (dir.getX() * i),
                        pLoc.getY() + y,
                        pLoc.getZ() + (dir.getZ() * i),
                        1, 0, 0, 0, 0
                );
            }
        }
    }

    public static boolean isEnabled(PointerConfiguration config) {
        return config.getParticle().isEnabled();
    }
}
