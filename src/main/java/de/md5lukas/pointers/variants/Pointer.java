package de.md5lukas.pointers.variants;

import de.md5lukas.pointers.TargetData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public abstract class Pointer {

    @Getter
    private final long interval;

    public void show(@NotNull Player player, @NotNull TargetData target) {
        update(player, target);
    }

    public void update(@NotNull Player player, @NotNull TargetData target) {

    }

    public void hide(@NotNull Player player, @NotNull TargetData target) {

    }
}
