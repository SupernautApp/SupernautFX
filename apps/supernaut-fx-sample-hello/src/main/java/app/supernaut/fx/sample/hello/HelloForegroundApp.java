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
package app.supernaut.fx.sample.hello;

import app.supernaut.fx.ApplicationDelegate;
import app.supernaut.fx.FxLauncher;
import app.supernaut.fx.FxmlLoaderFactory;
import app.supernaut.fx.test.NoopBackgroundApp;
import io.micronaut.context.annotation.Factory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.supernaut.logging.JavaLoggingSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.net.URL;

/**
 * A simple Supernaut.FX App implementing {@link ApplicationDelegate}.
 *
 * If is also annotated to be Micronaut {@link Factory}. This allows it to
 * create the {@link Named} {@code String} instance and to specify inclusion
 * of a {@code Bean} that is defined in a library, which will enable the Micronaut
 * annotation processor to include the generated code for that {@code Bean}.
 *
 * (It might be nice to abstract this "factory" capability somehow so apps don't need to
 * be  directly dependent on Micronaut for something so simple, but at this point using the @Factory
 * annotation seems to be the simplest way to do things.)
 */


@Singleton
@Factory
public class HelloForegroundApp implements ApplicationDelegate {
    private static final Logger log = LoggerFactory.getLogger(HelloForegroundApp.class);
    private final FxmlLoaderFactory loaderFactory;


    /**
     * Main method that calls launcher
     * @param args command-line args
     */
    public static void main(String[] args) {
        JavaLoggingSupport.configure(HelloForegroundApp.class, "app.supernaut.fx.sample.hello");
        FxLauncher.byName("micronaut").launch(args, HelloForegroundApp.class);
    }

    /**
     * Constructor
     * @param loaderFactory injected FXMLLoaderFactory
     */
    public HelloForegroundApp(FxmlLoaderFactory loaderFactory) {
        log.info("Constructing Hello");
        this.loaderFactory = loaderFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        log.info("Initializing Hello");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        log.info("Starting Hello");
        FXMLLoader loader = loaderFactory.get(getFXMLUrl("MainWindow.fxml"));
        log.debug("primaryStage root FXML: {}", loader.getLocation());
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setTitle("SupernautFX Hello");
        primaryStage.show();
    }

    /**
     * @return the planet name to great
     */
    @Singleton
    @Named("PLANETNAME")
    public String getPlanetName() {
        return "Mars";
    }

    /**
     * @return A "noop" background app
     */
    @Singleton
    public NoopBackgroundApp getBackgroundApp() {
        return new NoopBackgroundApp();
    }

    private URL getFXMLUrl(String fileName) {
        return HelloForegroundApp.class.getResource(fileName);
    }
}
