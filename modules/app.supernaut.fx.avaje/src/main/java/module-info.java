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
import app.supernaut.fx.avaje.AvajeFxLauncherProvider;

/**
 * Module implementing Supernaut.FX dependency injection with Avaje Inject
 *
 * @provides FxLauncherProvider with {@link AvajeFxLauncherProvider}
 */
module app.supernaut.fx.avaje {
    requires transitive app.supernaut.fx;
    
    requires javafx.graphics;
    requires javafx.fxml;

    requires org.slf4j;
    requires io.avaje.inject;

    exports app.supernaut.fx.avaje;
    //exports app.supernaut.fx.avaje.fxml;

    provides FxLauncherProvider with AvajeFxLauncherProvider;
}
