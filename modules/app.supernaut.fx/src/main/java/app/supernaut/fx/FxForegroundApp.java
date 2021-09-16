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
 *         Abstracts {@link Stage} with {@link FxMainView}. This also helps with testing.
 *     </li>
 *     <li>
 *         Supports faster, multi-threaded launching with the {@link FxLauncher} interface.
 *     </li>
 *     <li>
 *         Supports flexible construction of application object hierarchies using Dependency Injection provided
 *         by <b>Micronaut®</b> framework and possibly other D.I. frameworks in the future.
 *     </li>
 * </ol>
 * If you don't care to use the {@link FxMainView} abstraction, you can implement {@link FxApplicationCompat} and
 * implement the {@link FxApplicationCompat#start(Stage)} method which is compatible with {@link Application#start(Stage)}.
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
     * Adapt from OpenJFX-independent {@link ForegroundApp} to JFX-compatible {@link FxForegroundApp}
     * which requires a primary {@link Stage} to start.
     * 
     * @param mainView Must be a {@link FxMainView}
     * @throws Exception if something goes wrong
     * @throws IllegalArgumentException if mainView isn't a {@link FxMainView}
     */
    @Override
    default void start(SupernautMainView mainView) throws Exception {
        if (mainView instanceof FxMainView) {
            start(mainView);
        } else {
            throw new IllegalArgumentException("Main view must be implementation of " + FxMainView.class);
        }
    }

    /**
     * The main entry point for Supernaut.fx applications. Called from {@link Application#start(Stage)}.
     * At a minimum, you must implement this method.
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     *
     * @param mainView A wrapper view containing the primary {@link Stage}
     * @throws java.lang.Exception if something goes wrong
     */
    void start(FxMainView mainView) throws Exception;
    
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

    /**
     * A OpenJFX-compatible {@link SupernautMainView} that potentially contains a {@link Stage}.
     * We are trying to make having a {@link Stage} optional, because in test environments (and perhaps <b>macOS</b> apps
     * if someday OpenJFX gets better <b>macOS</b> support) the Stage may not be present.
     */
    interface FxMainView extends SupernautMainView {
        /**
         * Show the stage/view
         */
        void show();

        /**
         * @return The {@link Stage} this view is contained in (if any)
         */
        Optional<Stage> optionalStage();
    }

    /**
     * This interface makes converting from an instance of {@link Application} easier.
     * <p>Simply change:
     * <p>{@code class MyFXForegroundApp extends Application}
     * <p>to
     * <p>{@code class MyFXForegroundApp implements FxApplicationCompat}
     */
    interface FxApplicationCompat extends FxForegroundApp {
        /**
         * Start method compatible with OpenJFX start method
         *
         * @param primaryStage primary stage (unwrapped)
         */
        void start(Stage primaryStage);
        default void start(FxMainView mainView) {
            start(mainView.optionalStage().orElseThrow());
        }
    }

    /**
     * Implement this interface if you need access to the {@link Application} object instance
     */
    interface OpenJfxApplicationAware extends ForegroundApp {
        /**
         * Setter that will receive the {@link Application} instance
         * @param application reference to the OpenJFX application
         */
        void setJfxApplication(Application application);
    }
}
