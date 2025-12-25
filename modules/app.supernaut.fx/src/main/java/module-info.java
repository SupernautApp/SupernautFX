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

import app.supernaut.fx.FxLauncherProvider;
import app.supernaut.fx.sample.SimpleFxLauncherProvider;

/**
 * Defines the core classes and interfaces used by <b>Supernaut.FX</b> applications. These
 * interface extend the more abstract interfaces in {@link app.supernaut}. In general,
 * applications should not depend on classes defined in the {@code app.supernaut.fx.micronaut} module.
 * <p>
 * If you are writing a Supernaut.FX there are two main classes you need to know about: {@link app.supernaut.fx.ApplicationDelegate}
 * and {@link app.supernaut.fx.FxLauncher}. See {@link app.supernaut.fx.ApplicationDelegate} for an example app and how to get
 * started or read the User's Guide (TBD).
 *
 * @uses app.supernaut.fx.FxLauncher To launch applications with an implementing provider.
 */
module app.supernaut.fx {
    requires transitive app.supernaut;

    requires jakarta.inject;
    
    requires javafx.graphics;
    requires javafx.fxml;

    requires org.slf4j;

    exports app.supernaut.fx;
    exports app.supernaut.fx.fxml;
    exports app.supernaut.fx.services;
    exports app.supernaut.fx.test;
    exports app.supernaut.fx.util;
    exports app.supernaut.fx.sample;
    exports app.supernaut.fx.internal to javafx.graphics;

    uses FxLauncherProvider;
    provides FxLauncherProvider with SimpleFxLauncherProvider;
}