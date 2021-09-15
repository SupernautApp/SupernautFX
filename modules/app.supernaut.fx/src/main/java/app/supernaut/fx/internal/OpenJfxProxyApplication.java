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
package app.supernaut.fx.internal;

import app.supernaut.fx.FxLauncher;
import app.supernaut.fx.FxForegroundApp;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Internal <b>Supernaut.fx</b> implementation of {@link Application}. As a static proxy object for
 * {@link FxForegroundApp}, it delegates OpenJFX {@link Application} lifecycle calls to {@link FxForegroundApp}
 * and makes it more independent of OpenJFX.
 * 
 * <p>Tagline: <q>
 *  We subclass {@link javafx.application.Application} so you don't have to.
 * </q></p>
 * <p>
 * To create a Supernaut.fx app, write a class that implements {@link FxForegroundApp}.
 * </p>
 *
 */
public final class OpenJfxProxyApplication extends Application {
    private static final Logger log = LoggerFactory.getLogger(OpenJfxProxyApplication.class);
    public static FxLauncher configuredLauncher;  // Launcher must set this global before calling constructor
    protected final FxLauncher launcher;
    protected final FxForegroundApp foregroundApp;
    
    /**
     * Create a JavaFX application that wraps a SfxForegroundApp
     * Note that {@link FxLauncher#createForegroundApp(Application)} will wait
     * on the background app initialized latch so this constructor
     * will block until the background app is created and initialized.
     * Constructed on the JavaFX application thread
     */
    public OpenJfxProxyApplication() {
        launcher = configuredLauncher;
        foregroundApp = launcher.createForegroundApp(this);
    }

    /**
     * Supernaut.fx implementation of {@link Application#init}.
     * Initializes the ApplicationContext and loads and dependency injects the Application singleton.
     * Called on the JavaFX-launcher thread
     * @throws Exception if something goes wrong
     */
    @Override
    public void init() throws Exception {
        log.info("Initializing SfxForegroundApp");
        foregroundApp.init();
    }

    /**
     * Supernaut.fx implementation of {@link Application#start}.
     * Calls the application's implementation of {@link FxForegroundApp#start}
     *
     * @param primaryStage The primary Stage for the application
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("Starting SfxForegroundApp");
        foregroundApp.start(DefaultFxMainView.of(primaryStage));
    }

    /**
     * SupernautFX implementation of Application#stop().
     * Stops the SupernautFxApp and then stops the Micronaut ApplicationContext
     * @throws Exception if something goes wrong
     */
    @Override
    public void stop() throws Exception {
        log.info("Stopping SfxForegroundApp");
        foregroundApp.stop();
        // TODO: Should call a "stop" method in the launcher or the AppFactory?
        launcher.getBackgroundApp().get().stop();
    }
}
