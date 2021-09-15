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

import app.supernaut.Launcher;
import javafx.application.Application;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

/**
 * Additional required method for launching {@link FxForegroundApp} instances that
 * are proxied by {@code OpenJfxProxyApplication}.
 */
public interface FxLauncher extends Launcher {
    /**
     * Construct a {@link FxForegroundApp} that is a delegate to {@code OpenJfxProxyApplication}.
     * @param jfxApplication The OpenJfx "proxy" app instance
     * @return A newly constructed (and possibly injected) foreground app
     */
    FxForegroundApp createForegroundApp(Application jfxApplication);

    /**
     * Implementations must implement this method to return a unique name
     * @return A unique name for this DI-capable {@link Launcher} implementation
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
