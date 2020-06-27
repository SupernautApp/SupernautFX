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
module org.consensusj.supernautfx {
    requires javafx.graphics;
    requires javafx.fxml;
    requires io.micronaut.inject;
    requires javax.inject;

    requires org.slf4j;

    /* TODO: Move java.logging, test support to another module */
    requires java.logging;
    exports org.consensusj.supernaut.logging;
    exports org.consensusj.supernaut.test;

    /* TODO: supernaut subpackage should DEFINITELY BE a separate module */
    exports org.consensusj.supernaut;
    exports org.consensusj.supernaut.services;

    exports org.consensusj.supernautfx;
    exports org.consensusj.supernautfx.internal to javafx.graphics;

    /* TODO: Micronaut subpackage should maybe be internal or in a separate module? */
    exports org.consensusj.supernautfx.micronaut;
    /* TODO: Fix this */
    /* We have to open this to so Micronaut (possibly in the merged module) can @Inject private fields in it */
    opens org.consensusj.supernautfx.micronaut;
    exports org.consensusj.supernautfx.test;
}