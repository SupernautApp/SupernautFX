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
package app.supernaut.fx.sample.hello;

import app.supernaut.fx.FxLauncher;
import app.supernaut.fx.SfxForegroundApp;
import app.supernaut.fx.FxmlLoaderFactory;
import app.supernaut.fx.test.NoopBackgroundApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.supernaut.logging.JavaLoggingSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;

/**
 * A simple Supernaut.FX App implementing {@link SfxForegroundApp}.
 */
@Singleton
public class HelloForegroundApp implements SfxForegroundApp {
    private static final Logger log = LoggerFactory.getLogger(HelloForegroundApp.class);
    private final FxmlLoaderFactory loaderFactory;


    public static void main(String[] args) {
        JavaLoggingSupport.configure(HelloForegroundApp.class, "app.supernaut.fx.sample.testapp");
        FxLauncher.byName("micronaut").launch(args, HelloForegroundApp.class, NoopBackgroundApp.class);
    }
    
    public HelloForegroundApp(FxmlLoaderFactory loaderFactory) {
        log.info("Constructing Hello");
        this.loaderFactory = loaderFactory;
    }

    @Override
    public void init() {
        log.info("Initializing Hello");
    }

    @Override
    public void start(SfxMainView mainView) throws IOException {
        Stage primaryStage = mainView.optionalStage().orElseThrow();
        log.info("Starting Hello");
        FXMLLoader loader = loaderFactory.get(getFXMLUrl("MainWindow.fxml"));
        log.debug("primaryStage root FXML: {}", loader.getLocation());
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setTitle("SupernautFX Hello");
        primaryStage.show();
    }
    
    private URL getFXMLUrl(String fileName) {
        return HelloForegroundApp.class.getResource(fileName);
    }

}
