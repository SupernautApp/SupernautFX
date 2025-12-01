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
package app.supernaut.fx.sample;

import app.supernaut.BackgroundApp;
import app.supernaut.fx.ApplicationDelegate;
import app.supernaut.fx.FxLauncher;
import app.supernaut.fx.FxLauncherProvider;
import app.supernaut.fx.test.NoopAppDelegate;
import app.supernaut.fx.test.NoopBackgroundApp;

/**
 * This is pathologically simple because AppDelegate and BackgroundApp are hard-coded
 */
public class SimpleFxLauncherProvider implements FxLauncherProvider {
    @Override
    public String id() {
        return "simple";
    }

    @Override
    public FxLauncher launcher() {
        return new SimpleFxLauncher(new DefaultAppFactory(NoopAppDelegate.class, NoopBackgroundApp.class));
    }

    ///  A "Provider" must have a no-arg constructor
    public SimpleFxLauncherProvider() {
    }
}
