/*
 * Copyright 2019-2022 M. Sean Gilligan.
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

import java.util.concurrent.CompletableFuture;

/**
 * Launcher for Supernaut.FX (JavaFX) applications. By using this launcher, your applications
 * can <i>implement</i> the {@link ApplicationDelegate} interface instead of <i>extending</i>
 * {@link Application} and have their constructor dependency injected -- see {@link ApplicationDelegate} for
 * an explanation of the advantages and details of this approach.
 */
public interface FxLauncher {
    ///  New synchronous launch method
    void launch(String[] args);

    ///  New Async launch method
    CompletableFuture<ApplicationDelegate> launchAsync(String[] args);

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
}
