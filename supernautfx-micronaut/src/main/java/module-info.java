/*
 * Copyright 2019-2020 M. Sean Gilligan.
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
 * Supernaut.FX module.
 */
module app.supernaut.fx.micronaut {
    requires transitive app.supernaut.fx;

    requires java.logging;

    requires javafx.graphics;
    requires javafx.fxml;

    requires javax.inject;

    requires io.micronaut.inject;
    requires org.slf4j;
    
    exports app.supernaut.fx.micronaut;
    /* TODO: Fix this */
    /* We have to open this to so Micronaut (possibly in the merged module) can @Inject private fields in it */
    opens app.supernaut.fx.micronaut;

    provides app.supernaut.fx.FxLauncher with app.supernaut.fx.micronaut.MicronautFxLauncher;
}