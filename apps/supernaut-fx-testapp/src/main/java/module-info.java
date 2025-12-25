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

/**
 * Module descriptor for SupernautFX Test App
 */
module app.supernaut.fx.testapp {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires app.supernaut.fx;

    requires jakarta.inject;
    requires io.micronaut.inject;
    requires jakarta.annotation;

    requires org.slf4j;

    opens app.supernaut.fx.testapp to javafx.graphics, javafx.fxml, java.base;
    exports app.supernaut.fx.testapp;

    uses FxLauncherProvider;
}
