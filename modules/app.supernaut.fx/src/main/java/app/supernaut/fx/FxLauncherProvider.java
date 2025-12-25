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
import app.supernaut.fx.sample.SimpleFxLauncher;
import app.supernaut.fx.test.NoopBackgroundApp;
import javafx.application.Application;

import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;

///  Launcher Provider
public interface FxLauncherProvider {
    /**
     * Implementations must implement this method to return a unique name
     * @return A unique name for this DI-capable {@link FxLauncher} implementation
     */
    String id();

    FxLauncher launcher();

    /**
     * Interface that can be used to create and pre-initialize {@link ApplicationDelegate} and {@link BackgroundApp}.
     * This interface can be implemented by subclasses (or direct callers of the constructor.) By "pre-initialize" we
     * mean call implementation-dependent methods prior to {@code init()} or {@code start()}.
     * This interface is designed to support using Dependency Injection frameworks like Micronaut, see
     * {@code MicronautSfxLauncher}.
     */
    interface AppFactory {
        /**
         * Create the background class instance from a {@link Class} object
         * @return application instance
         */
        BackgroundApp   createBackgroundApp();

        /**
         * Create the app delegate class instance from a {@link Class} object
         * @param proxyApplication a reference to the proxy {@link Application} created by Supernaut.FX
         * @return application instance
         */
        ApplicationDelegate createAppDelegate(Application proxyApplication);
    }

    /**
     * Default implementation of AppFactory. Creates BackgroundApp and AppDelegate with newInstance().
     */
    class DefaultAppFactory implements AppFactory {
        private final Class<? extends ApplicationDelegate> appDelegateClass;
        private final Class<? extends BackgroundApp> backgroundAppClass;

        public DefaultAppFactory(Class<? extends ApplicationDelegate> appDelegateClass, Class<? extends BackgroundApp> backgroundAppClass) {
            this.appDelegateClass = appDelegateClass;
            this.backgroundAppClass = backgroundAppClass;
        }

        @Override
        public BackgroundApp createBackgroundApp() {
            return newInstance(backgroundAppClass);
        }

        @Override
        public ApplicationDelegate createAppDelegate(Application proxyApplication) {
            return newInstance(appDelegateClass);
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
            T appDelegate;
            try {
                appDelegate = clazz.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            return appDelegate;
        }
    }


    /**
     * Find a FxLauncher provider by name
     *
     * @param id Name (e.g. "micronaut")
     * @return an FxLauncher instance
     * @throws NoSuchElementException if not found
     */
    static FxLauncherProvider byId(String id) {
        return findFirst(launcher -> launcher.id().equals(id))
                .orElseThrow(() -> new NoSuchElementException("LauncherFactory " + id + " not found."));
    }

    /**
     * Find default FxLauncher
     *
     * @return an FxLauncher instance
     * @throws NoSuchElementException if not found
     */
    static FxLauncherProvider find() {
        return findFirst(FxLauncherProvider::defaultFilter)
                .orElseThrow(() -> new NoSuchElementException("Default Launcher not found."));
    }

    /**
     * Find a launcher using a custom predicate
     * @param filter predicate for finding a launcher
     * @return the <b>first</b> launcher matching the predicate, if any
     */
    static Optional<FxLauncherProvider> findFirst(Predicate<FxLauncherProvider> filter) {
        ServiceLoader<FxLauncherProvider> loader = ServiceLoader.load(FxLauncherProvider.class);
        return loader.stream()
                .map(ServiceLoader.Provider::get)
                .filter(FxLauncherProvider::defaultFilter)
                .findFirst();
    }

    /**
     * Find the first available launcher that isn't the {@link SimpleFxLauncher}
     * @param launcher a candidate launcher
     * @return true if it should be "found"
     */
    private static boolean defaultFilter(FxLauncherProvider launcher) {
        return !launcher.id().equals("simple");
    }
}
