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
package app.supernaut.fx.micronaut;

import app.supernaut.fx.ApplicationDelegate;
import app.supernaut.fx.FxLauncherProvider;
import app.supernaut.BackgroundApp;
import app.supernaut.fx.FxLauncherAbstract;

/**
 * A launcher that uses <a href="https://micronaut.io">Micronaut@ framework</a> to instantiate and Dependency Inject
 * the foreground and background applications.
 */
public class MicronautFxLauncher extends FxLauncherAbstract {
    /**
     * Default constructor that initializes the background app on its own thread.
     */
    public MicronautFxLauncher() {
        this(new MicronautFxAppFactory(false), true);
    }

    /**
     *
     * @param initializeBackgroundAppOnNewThread If true, initializes {@code appFactorySupplier} and
     *        {@code BackgroundApp} on new thread, if false start them on calling thread (typically the main thread)
     */
    public MicronautFxLauncher(FxLauncherProvider.AppFactory appFactory, boolean initializeBackgroundAppOnNewThread) {
        super(appFactory, initializeBackgroundAppOnNewThread);
    }
}
