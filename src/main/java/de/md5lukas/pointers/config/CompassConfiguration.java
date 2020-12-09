package de.md5lukas.pointers.config;

import de.md5lukas.pointers.config.storage.CompassLocationStorage;
import de.md5lukas.pointers.config.storage.InMemoryCompassLocationStorage;
import lombok.*;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class CompassConfiguration {

    private boolean enabled = false;

    @Nullable
    private Location resetTarget = null;

    @NotNull
    @NonNull
    private CompassLocationStorage locationStorage = new InMemoryCompassLocationStorage();

    void loadFromConfiguration(ConfigurationSection cfg) throws InvalidConfigurationException {
        try {
            setEnabled(cfg.getBoolean("enabled"));

            Object uncheckedResetTarget = cfg.get("resetTarget");
            if (uncheckedResetTarget instanceof Location)
                setResetTarget((Location) uncheckedResetTarget);
        } catch (Exception e) {
            throw new InvalidConfigurationException("Invalid configuration in the compass pointer configuration", e);
        }
    }
}
