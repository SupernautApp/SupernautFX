/*
 * Copyright 2019-2021 M. Sean Gilligan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.supernaut;

import java.util.concurrent.CompletableFuture;

/**
 * Improved, asynchronous two-phase application launcher. It is logically-compatible with the JavaFX launcher.
 * See subclass {@code FxLauncher} and its implementations for full JavaFX support.
 *
 * It uses threads to start a background app as quickly as possible and
 * possibly before the foreground app is started. This background app can make network
 * requests while the foreground app is starting up, so that views in
 * the foreground app can be updated with live data as soon as possible.
 */
public interface Launcher {
    /**
     * Launch and run the application on the current thread.
     * Does not return until after foreground app closes.
     * @param args command-line args
     * @param foregroundApp class object for ForegroundApp
     * @param backgroundApp class object for BackgroundApp
     */
    void launch(String[] args, Class<? extends ForegroundApp> foregroundApp, Class<? extends BackgroundApp> backgroundApp);

    /**
     * Launch and run the application on the current thread. Uses default/no-op background application.
     * Does not return until after foreground app closes.
     * @param args command-line args
     * @param foregroundApp class object for ForegroundApp
     */
    void launch(String[] args, Class<? extends ForegroundApp> foregroundApp);

    /**
     * Launch and run the application on a newly created thread.
     * This method is useful for testing and possibly for other
     * application startup scenarios.
     *
     * @param args command-line args
     * @param foregroundApp class object for ForegroundApp
     * @param backgroundApp class object for BackgroundApp
     * @return A future that is completed when Foreground app is initialized
     */
    CompletableFuture<ForegroundApp> launchAsync(String[] args, Class<? extends ForegroundApp> foregroundApp, Class<? extends BackgroundApp> backgroundApp);

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
