package de.md5lukas.pointers;

import de.md5lukas.pointers.variants.Pointer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.logging.Level;

@RequiredArgsConstructor
final class PointerRunner implements Runnable {

    @NotNull
    private final Pointer pointer;
    @NotNull
    private final Pointers pointers;

    @Override
    public void run() {
        try {
            for (Map.Entry<@NotNull Player, @NotNull TargetData> entry : pointers.getActivePointersView().entrySet()) {
                pointer.update(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            pointers.getPlugin().getLogger().log(Level.SEVERE, "An exception occurred while executing the update method of an pointer", e);
        }
    }
}
