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
 * A background application that is started before the foreground app
 * and can communicate with the foreground app. The background application
 * is started before the UI toolkit is initialized and can make its first
 * network requests simultaneously with loading the UI toolkit and
 * the foreground UI application.
 *
 * In the JavaFX implementation this is an alternative to the PreLoader,
 * which was essentially designed for applications that start synchronously. In
 * an asynchronous/reactive JavaFX application the primaryStage should
 * be displayed immediately and updated with data as data becomes available.
 * The important thing is that network requests be sent as soon as possible
 * after {@code main} is called.
 *
 */
public interface BackgroundApp {
    /**
     * Override to do any (hopefully minimal and quick) initialization
     * that you want to happen before the ForegroundApp is started
     */
    default void init() {};

    /**
     * Override this to create your own background threads and do any
     * longer-duration initialization or start network I/O, etc.
     */
    void start();

    /**
     * Override to get called when the application is stopping.
     */
    default void stop() {}
}
