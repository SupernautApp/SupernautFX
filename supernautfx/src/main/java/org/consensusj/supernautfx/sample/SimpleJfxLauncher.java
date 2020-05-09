package org.consensusj.supernautfx.sample;

import org.consensusj.supernaut.BackgroundApp;
import org.consensusj.supernautfx.SfxLauncher;
import org.consensusj.supernautfx.SfxForegroundApp;

import java.lang.reflect.InvocationTargetException;

/**
 * A simple {@link SfxLauncher} that uses {@link Class} objects to specify {@link BackgroundApp}
 * and {@link SfxForegroundApp}.
 */
public final class SimpleJfxLauncher extends SfxLauncher {
    public SimpleJfxLauncher(Class<? extends BackgroundApp> backgroundAppClass,
                             Class<? extends SfxForegroundApp> foregroundAppClass,
                             boolean backgroundStart) {
        super(() -> new LambdaAppFactory(
                        () -> newInstance(backgroundAppClass),
                        () -> newInstance(foregroundAppClass)
                ),
                backgroundStart);
    }

    /**
     * newInstance without checked exceptions.
     *
     * @param clazz A Class object that must have a no-args constructor.
     * @param <T> The type of the class
     * @return A new instanceof the class
     * @throws RuntimeException exceptions thrown by {@code newInstance()}.
     */
    private static <T> T newInstance(Class<T> clazz) {
        T foregroundApp;
        try {
            foregroundApp = clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return foregroundApp;
    }
}
