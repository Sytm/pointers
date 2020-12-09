package de.md5lukas.pointers;

import de.md5lukas.pointers.config.PointerConfiguration;
import de.md5lukas.pointers.variants.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class Pointers implements Listener {

    @NotNull
    private static final List<@NotNull RegisteredPointer> pointers = new ArrayList<>();

    static {
        pointers.add(new RegisteredPointer(ActionBarPointer::isEnabled, ActionBarPointer::new));
        pointers.add(new RegisteredPointer(BeaconPointer::isEnabled, BeaconPointer::new));
        pointers.add(new RegisteredPointer(BlinkingBlockPointer::isEnabled, BlinkingBlockPointer::new));
        pointers.add(new RegisteredPointer(CompassPointer::isEnabled, CompassPointer::new));
        pointers.add(new RegisteredPointer(ParticlePointer::isEnabled, ParticlePointer::new));
    }

    @Getter
    @NotNull
    private final Plugin plugin;
    @Getter
    @NotNull
    private final PointerConfiguration pointerConfiguration;
    @NotNull
    private final List<@NotNull Pointer> enabledPointers = new ArrayList<>();
    @NotNull
    private final Map<@NotNull Player, @NotNull TargetData> activePointers = new HashMap<>();
    @Getter
    @NotNull
    private final Map<@NotNull Player, @NotNull TargetData> activePointersView = Collections.unmodifiableMap(activePointers);

    public Pointers(@NotNull @NonNull Plugin plugin, @NotNull @NonNull PointerConfiguration pointerConfiguration) {
        this.plugin = plugin;
        this.pointerConfiguration = pointerConfiguration;

        setupTasks();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void enable(@NotNull @NonNull Player player, @NotNull @NonNull TargetData targetData) {
        val previous = activePointers.get(player);
        if (previous != null) {
            hide(player, previous);
        }
        activePointers.put(player, targetData);
        show(player, targetData);
    }

    public void disable(@NotNull @NonNull Player player) {
        val previous = activePointers.get(player);
        if (previous != null) {
            hide(player, previous);
            activePointers.remove(player);
        }
    }

    private void setupTasks() {
        for (val registeredPointer : pointers) {
            if (registeredPointer.isEnabled(pointerConfiguration)) {
                val pointer = registeredPointer.create(this);
                enabledPointers.add(pointer);
                if (pointer.getInterval() > 0) {
                    plugin.getServer().getScheduler().runTaskTimer(plugin, new PointerRunner(pointer, this), 0, pointer.getInterval());
                }
            }
        }
    }

    private void show(@NotNull Player player, @NotNull TargetData targetData) {
        for (val pointer : enabledPointers) {
            pointer.show(player, targetData);
        }
    }

    private void hide(@NotNull Player player, @NotNull TargetData targetData) {
        for (val pointer : enabledPointers) {
            pointer.hide(player, targetData);
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        disable(event.getPlayer());
    }
}
