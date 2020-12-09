package de.md5lukas.pointers.config;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;

@Getter
public final class PointerConfiguration {

    @NotNull
    private final ActionBarConfiguration actionBar = new ActionBarConfiguration();

    @NotNull
    private final BeaconConfiguration beacon = new BeaconConfiguration();

    @NotNull
    private final BlinkingBlockConfiguration blinkingBlock = new BlinkingBlockConfiguration();

    @NotNull
    private final CompassConfiguration compass = new CompassConfiguration();

    @NotNull
    private final ParticleConfiguration particle = new ParticleConfiguration();

    public void loadFromConfiguration(ConfigurationSection config) throws InvalidConfigurationException {
        actionBar.loadFromConfiguration(config.getConfigurationSection("actionBar"));
        beacon.loadFromConfiguration(config.getConfigurationSection("beacon"));
        blinkingBlock.loadFromConfiguration(config.getConfigurationSection("blinkingBlock"));
        compass.loadFromConfiguration(config.getConfigurationSection("compass"));
        particle.loadFromConfiguration(config.getConfigurationSection("particle"));
    }
}
