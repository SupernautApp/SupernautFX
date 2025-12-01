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
package app.supernaut.fx.avaje;

import app.supernaut.BackgroundApp;
import app.supernaut.fx.ApplicationDelegate;
import app.supernaut.fx.FxLauncher;
import app.supernaut.fx.FxLauncherProvider;

/**
 *
 */
public class AvajeFxLauncherProvider implements FxLauncherProvider {
    @Override
    public String id() {
        return "avaje-inject";
    }

    @Override
    public FxLauncher launcher(Class<? extends ApplicationDelegate> appDelegateClass, Class<? extends BackgroundApp> backgroundAppClass) {
        // Ignore the passed in classes -- this method is probably unused and the classes should be annotated, so we'll find them anyway
        return new AvajeFxLauncher(true);
    }

    // TODO: I don't think we really need appFactory passed in, but that's the current API of the superclass...
    @Override
    public FxLauncher launcher(AppFactory appFactory) {
        return new AvajeFxLauncher(appFactory, true);
    }

    /// Providers must have no-arg constructor
    public AvajeFxLauncherProvider() {
    }
}
