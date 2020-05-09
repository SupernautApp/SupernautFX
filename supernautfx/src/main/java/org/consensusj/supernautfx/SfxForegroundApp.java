/*
 * Copyright 2019-2020 Michael Sean Gilligan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.consensusj.supernautfx;

import javafx.application.Application;
import javafx.stage.Stage;
import org.consensusj.supernaut.ForegroundApp;

import java.util.Optional;

/**
 * <b>Supernaut.fx</b> specialization of <b>Supernaut</b> {@link ForegroundApp}. Supernaut.fx {@link SfxForegroundApp}s
 * implement this interface instead of subclassing <b>OpenJFX</b> {@link Application}. This has several
 * advantages over directly extending {@link javafx.application.Application}:
 * <ol>
 *     <li>
 *       Applications {@code implement} an {@code interface} rather than {@code extend} a {@code class}.
 *       This increases the testability and architectural flexibility of the application.
 *     </li>
 *     <li>
 *         Abstracts {@link Stage} with {@link SfxMainView}. This also helps with testing.
 *     </li>
 *     <li>
 *         Supports faster, multi-threaded launching with {@link SfxLauncher}.
 *     </li>
 *     <li>
 *         Supports flexible construction of application object hierarchies with {@link SfxLauncher} and
 *         the {@link SfxLauncher.AppFactory} interface, including Dependency Injection with <b>Micronaut</b>
 *         and possibly other DI frameworks in the future.
 *     </li>
 * </ol>
 * 
 * Summary: Implement the {@link SfxForegroundApp} interface instead of subclassing OpenJFX {@link Application}. Your
 * apps will be faster and looser, but also tight. You can freely use and abuse inheritance as you please. You also
 * can get your constructor injected, which is always fun.
 */
public interface SfxForegroundApp extends ForegroundApp {
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
     * Adapt from OpenJFX-independent {@link ForegroundApp} to JFX-compatible {@link SfxForegroundApp}
     * which requires a primary {@link Stage} to start.
     * 
     * @param mainView Must be a {@link SfxMainView}
     * @throws Exception if something goes wrong
     * @throws IllegalArgumentException if mainView isn't a {@link SfxMainView}
     */
    @Override
    default void start(SupernautMainView mainView) throws Exception {
        if (mainView instanceof SfxMainView) {
            start(mainView);
        } else {
            throw new IllegalArgumentException("Main view must be implementation of " + SfxMainView.class);
        }
    }

    /**
     * The main entry point for Supernaut.fx applications. Called from the OpenJFX
     * start() method.  At a minimum, you must implement this method.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param mainView A wrapper view containing the primary {@link Stage}
     * @throws java.lang.Exception if something goes wrong
     */
    void start(SfxMainView mainView) throws Exception;
    
    /**
     * This method is called when the application should stop, and provides a
     * convenient place to prepare for application exit and destroy resources.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     * @throws java.lang.Exception if something goes wrong
     */
    @Override
    default void stop() throws Exception {};

    /**
     * A OpenJfx-compatible {@link SupernautMainView} that potentially contains a {@link Stage}.
     * We are trying to make having a {@link Stage} optional, because in test environments (and perhaps <b>macOS</b> apps
     * if someday OpenJFX gets better <b>macOS</b> support) the Stage may not be present.
     */
    interface SfxMainView extends SupernautMainView {
        void show();

        /**
         * @return The {@link Stage} this view is contained in (if any)
         */
        Optional<Stage> optionalStage();
    }

    /**
     * This interface makes converting from an instance of {@link Application} easier.
     * <p>Simply change</p>
     * <p>{@code class MyJFXForegroundApp extends Application}</p>
     * <p>to</p>
     * <p>{@code class MyJFXForegroundApp implements SfxApplicationCompat}</p>
     */
    interface SfxApplicationCompat extends SfxForegroundApp {
        void start(Stage primaryStage);
        default void start(SfxMainView mainView) {
            start(mainView.optionalStage().orElseThrow());
        }
    }

    /**
     * Implement this interface if you need access to the {@link Application} object instance
     */
    interface OpenJfxApplicationAware extends ForegroundApp {
        void setJfxApplication(Application application);
    }
}
