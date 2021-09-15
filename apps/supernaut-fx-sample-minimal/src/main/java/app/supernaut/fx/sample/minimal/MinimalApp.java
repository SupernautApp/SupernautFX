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
package app.supernaut.fx.sample.minimal;

import app.supernaut.fx.FxLauncher;
import app.supernaut.fx.FxForegroundApp;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import jakarta.inject.Singleton;

/**
 * A minimal (Single-Class) Supernaut.FX App implementing {@link FxForegroundApp}.
 */
@Singleton
public class MinimalApp implements FxForegroundApp {
    private final String appName;

    /**
     * Main method that calls launcher
     * @param args command-line args
     */
    public static void main(String[] args) {
        FxLauncher.byName("micronaut").launch(args, MinimalApp.class);
    }

    /**
     * Constructor
     * @param config injected app configuration
     */
    public MinimalApp(AppConfig config) {
        appName = config.appName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(FxMainView mainView) {
        Stage primaryStage = mainView.optionalStage().orElseThrow();

        var label = new Label("Hello, " + appName + " app!");
        var scene = new Scene(new StackPane(label), 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SupernautFX Minimal App");
        primaryStage.show();
    }

    /**
     * An example object that is constructed by the DI framework and injected.
     */
    @Singleton
    public static class AppConfig {
        /** the application name */
        public final String appName = "Minimal";
    }
}
