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
package org.consensusj.supernaut;

/**
 *  An abstraction of a User Interface application that is separated
 *  into a foreground app and a background app. It is logically-compatible
 *  with JavaFX, an implementation using JavaFX is provided.
 */
public interface ForegroundApp {
    default void init() throws Exception {
    }

    void start(SupernautMainView view) throws Exception;

    default void stop() throws Exception {
    }

    /**
     * Marker interface for a view. In JavaFX this is a JavaFX "controller".
     */
    interface SupernautView {
    }

    /**
     * Marker interface for Main View. In JavaFX this is the view contained
     * in the primary {@code Stage}.
     */
    interface SupernautMainView extends SupernautView {
    }
}
