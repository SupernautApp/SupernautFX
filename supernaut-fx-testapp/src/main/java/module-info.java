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
 * Module descriptor for SupernautFX Test App
 */
module app.supernaut.fx.testapp {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires app.supernaut.fx;
    requires javax.inject;
    /*
     * Needed for the @Factory annotation and for the Bean Definition classes generated
     * by the Micronaut annotation processor. Most classes in this module only use annotations
     * from javax.inject and do not import any Micronaut classes. In general controllers and services
     * in a Supernaut.FX application should avoid dependencies on Micronaut and use javax.inject
     * annotations.
     */
    requires io.micronaut.inject;

    requires org.slf4j;
    requires java.logging;

    opens app.supernaut.fx.testapp to javafx.graphics, javafx.fxml, java.base;
    exports app.supernaut.fx.testapp;

    uses app.supernaut.fx.FxLauncher;
}