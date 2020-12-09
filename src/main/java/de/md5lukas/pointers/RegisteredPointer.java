package de.md5lukas.pointers;

import de.md5lukas.pointers.config.PointerConfiguration;
import de.md5lukas.pointers.variants.Pointer;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@RequiredArgsConstructor
final class RegisteredPointer {

    @NotNull
    private final Function<@NotNull PointerConfiguration, Boolean> isEnabledCheck;
    @NotNull
    private final Function<@NotNull Pointers, @NotNull Pointer> factory;

    public boolean isEnabled(@NotNull PointerConfiguration pointerConfiguration) {
        return isEnabledCheck.apply(pointerConfiguration);
    }

    @NotNull
    public Pointer create(Pointers pointers) {
        return factory.apply(pointers);
    }
}
