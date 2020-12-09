package de.md5lukas.pointers.config;

import de.md5lukas.pointers.config.storage.CompassLocationStorage;
import de.md5lukas.pointers.config.storage.InMemoryCompassLocationStorage;
import lombok.*;
import org.bukkit.Location;
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
}
