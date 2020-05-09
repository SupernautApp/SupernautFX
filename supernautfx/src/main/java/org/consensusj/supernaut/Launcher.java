package org.consensusj.supernaut;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Improved, asynchronous two-phase application launcher. It is logically-compatible with the JavaFX launcher.
 * A version that uses JavaFX is provided as an implementation.
 *
 * Uses threads to start a background app as quickly as possible and
 * possibly before the foreground app is started. This background app can make network
 * requests while the foreground app is starting up, so that views in
 * the foreground app can be updated with live data as soon as possible.
 */
public interface Launcher {
    static Optional<Launcher> findFirst(String name) {
        // TODO: Provide a static ServiceLoader method to load a launcher
        return Optional.empty();
    }

    /**
     * Launch and run the application on the current thread.
     * Does not return until after foreground app closes.
     * @param args command-line args
     */
    void launch(String[] args);

    /**
     * Launch and run the application on a newly created thread.
     * This method is useful for testing and possibly for other
     * application startup scenarios.
     *
     * @param args command-line args
     * @return A future that is completed when Foreground app is initialized
     */
    CompletableFuture<ForegroundApp> launchAsync(String[] args);

    /**
     * Get a future that will be completed when the Foreground app
     * is initialized.
     *
     * @return A future that is completed when Foreground app is initialized
     */
    CompletableFuture<ForegroundApp> getForegroundApp();

    /**
     * Get a future that will be completed when the Background app
     * is initialized.
     *
     * @return A future that is completed when Background app is initialized
     */
    CompletableFuture<BackgroundApp> getBackgroundApp();
}
