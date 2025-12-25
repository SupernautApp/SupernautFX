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
import io.avaje.inject.spi.InjectExtension;

module app.supernaut.sample.avaje.hello {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires app.supernaut.fx;
    requires app.supernaut.fx.avaje;

    requires jakarta.inject;
    requires io.avaje.inject;

    requires org.slf4j;

    opens app.supernaut.sample.avaje.hello to javafx.graphics, java.base, javafx.fxml;
    exports app.supernaut.sample.avaje.hello;

    provides InjectExtension with app.supernaut.sample.avaje.hello.HelloModule;

    uses FxLauncherProvider;
}
