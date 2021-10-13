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
package app.supernaut.fx;

import app.supernaut.BackgroundApp;
import javafx.application.Application;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.concurrent.CompletableFuture;

/**
 * Launcher for Supernaut.FX (JavaFX) applications. By using this launcher, your applications
 * can <i>implement</i> the {@link ApplicationDelegate} interface instead of <i>extending</i>
 * {@link Application} and have their constructor dependency injected -- see {@link ApplicationDelegate} for
 * an explanation of the advantages and details of this approach.
 */
public interface FxLauncher {
    /**
     * Launch and run the application on the current thread.
     * Does not return until after ApplicationDelegate closes.
     * @param args command-line args
     * @param appDelegate class object for ApplicationDelegate
     * @param backgroundApp class object for BackgroundApp
     */
    void launch(String[] args, Class<? extends ApplicationDelegate> appDelegate, Class<? extends BackgroundApp> backgroundApp);

    /**
     * Launch and run the application on the current thread. Uses default/no-op background application.
     * Does not return until after ApplicationDelegate closes.
     * @param args command-line args
     * @param appDelegate class object for ApplicationDelegate
     */
    void launch(String[] args, Class<? extends ApplicationDelegate> appDelegate);

    /**
     * Launch and run the application on a newly created thread.
     * This method is useful for testing and possibly for other
     * application startup scenarios.
     *
     * @param args command-line args
     * @param appDelegate class object for ApplicationDelegate
     * @param backgroundApp class object for BackgroundApp
     * @return A future that is completed when ApplicationDelegate app is initialized
     */
    CompletableFuture<ApplicationDelegate> launchAsync(String[] args, Class<? extends ApplicationDelegate> appDelegate, Class<? extends BackgroundApp> backgroundApp);

    /**
     * Get a future that will be completed when the ApplicationDelegate
     * is initialized.
     *
     * @return A future that is completed when ApplicationDelegate is initialized
     */
    CompletableFuture<ApplicationDelegate> getAppDelegate();

    /**
     * Get a future that will be completed when the Background app
     * is initialized.
     *
     * @return A future that is completed when Background app is initialized
     */
    CompletableFuture<BackgroundApp> getBackgroundApp();

    /**
     * Construct a {@link ApplicationDelegate} that is a delegate to {@code OpenJfxProxyApplication}.
     * @param jfxApplication The OpenJfx "proxy" app instance
     * @return A newly constructed (and possibly injected) ApplicationDelegate
     */
    ApplicationDelegate createAppDelegate(Application jfxApplication);

    /**
     * Implementations must implement this method to return a unique name
     * @return A unique name for this DI-capable {@link FxLauncher} implementation
     */
    String name();

    /**
     * Find a FxLauncher provider by name
     *
     * @param name Name (e.g. "micronaut")
     * @return an FxLaunder instance
     * @throws NoSuchElementException if not found
     */
    static FxLauncher byName(String name) {
        ServiceLoader<FxLauncher> loaders = ServiceLoader.load(FxLauncher.class);
        return loaders.stream()
                .map(ServiceLoader.Provider::get)
                .filter(launcher -> launcher.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Launcher '" + name + "' not found."));
    }
}
