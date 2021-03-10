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

import app.supernaut.fx.sample.SimpleFxLauncher;

/**
 * Supernaut.FX module.
 */
module app.supernaut.fx {
    requires transitive app.supernaut;

    requires javafx.graphics;
    requires javafx.fxml;

    requires org.slf4j;

    exports app.supernaut.fx;
    exports app.supernaut.fx.services;
    exports app.supernaut.fx.test;
    exports app.supernaut.fx.internal to javafx.graphics;

    uses app.supernaut.fx.FxLauncher;
    provides app.supernaut.fx.FxLauncher with SimpleFxLauncher;
}