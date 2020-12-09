package de.md5lukas.pointers.config;

import lombok.*;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    public void setInterval(int interval) {
        if (interval <= 0)
            throw new IllegalArgumentException("The interval cannot be less or equal to zero (" + interval + ")");
        this.interval = interval;
    }

    public void setAmount(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("The amount cannot be less or equal to zero (" + amount + ")");
        this.amount = amount;
    }

    public void setLength(double length) {
        if (length <= 0)
            throw new IllegalArgumentException("The length cannot be less or equal to zero (" + length + ")");
        this.length = length;
    }

    void loadFromConfiguration(ConfigurationSection cfg) throws InvalidConfigurationException {
        try {
            setEnabled(cfg.getBoolean("enabled"));

            setInterval(cfg.getInt("interval"));

            setHeightOffset(cfg.getDouble("heightOffset"));

            setShowVerticalDirection(cfg.getBoolean("showVerticalDirection"));

            setAmount(cfg.getInt("amount"));

            setLength(cfg.getDouble("length"));

            val particleName = cfg.getString("particle");
            if (particleName == null)
                throw new IllegalArgumentException("The particle must be provided");

            setParticle(Particle.valueOf(particleName.toUpperCase()));
        } catch (Exception e) {
            throw new InvalidConfigurationException("Invalid configuration in the particle pointer configuration", e);
        }
    }
}
