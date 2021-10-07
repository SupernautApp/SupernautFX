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
package app.supernaut.fx;

import javafx.application.Application;
import javafx.stage.Stage;
import app.supernaut.ForegroundApp;

import java.util.Optional;

/**
 * <b>Supernaut.FX</b> applications must implement this interface.
 * <p>
 * This is the <b>Supernaut.FX</b> specialization of <b>Supernaut</b> {@link ForegroundApp}. Supernaut.FX {@link FxForegroundApp}s
 * implement this interface instead of subclassing <b>JavaFX</b> {@link Application}. This has several
 * advantages over directly extending {@link javafx.application.Application}:
 * <ol>
 *     <li>
 *       Applications {@code implement} an {@code interface} rather than {@code extend} a {@code class}.
 *       This increases the testability and architectural flexibility of the application.
 *     </li>
 *     <li>
 *         Supports faster, multi-threaded launching with the {@link FxLauncher} interface.
 *     </li>
 *     <li>
 *         Supports flexible construction of application object hierarchies using Dependency Injection provided
 *         by <b>Micronaut®</b> framework and possibly other D.I. frameworks in the future.
 *     </li>
 * </ol>
 */
public interface FxForegroundApp extends ForegroundApp {
    /**
     * The application initialization method. This method is called from the JavaFX
     * init() method after the dependency injection context is initialized and the
     * application is constructed and dependency injected.
     *
     * <p>
     * NOTE: This method is not called on the JavaFX Application Thread. An
     * application must not construct a Scene or a Stage in this
     * method.
     * An application may construct other JavaFX objects in this method.
     * </p>
     * @throws java.lang.Exception if something goes wrong
     */
    @Override
    default void init() throws Exception {};

    /**
     * The main entry point for Supernaut.fx applications. Called from {@link Application#start(Stage)}.
     * At a minimum, you must implement this method.
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     *
     * @param primaryStage the primary stage
     * @throws Exception something went wrong
     */
    void start(Stage primaryStage) throws Exception;
    
    /**
     * This method is called when the application should stop, and provides a
     * convenient place to prepare for application exit and destroy resources.
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     *
     * @throws java.lang.Exception if something goes wrong
     */
    @Override
    default void stop() throws Exception {};
}
