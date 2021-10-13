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

/**
 * Module implementing Supernaut.FX dependency injection with Micronaut
 *
 * @provides app.supernaut.fx.FxLauncher with {@link app.supernaut.fx.micronaut.MicronautFxLauncher}
 */
module app.supernaut.fx.micronaut {
    requires transitive app.supernaut.fx;
    
    requires javafx.graphics;
    requires javafx.fxml;

    requires io.micronaut.inject;
    requires org.slf4j;
    
    exports app.supernaut.fx.micronaut;
    exports app.supernaut.fx.micronaut.fxml;

    provides app.supernaut.fx.FxLauncher with app.supernaut.fx.micronaut.MicronautFxLauncher;
}