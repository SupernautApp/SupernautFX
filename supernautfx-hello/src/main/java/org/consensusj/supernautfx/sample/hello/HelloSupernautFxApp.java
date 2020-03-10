package org.consensusj.supernautfx.sample.hello;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.consensusj.supernautfx.FxmlLoaderFactory;
import org.consensusj.supernautfx.SupernautFxApp;
import org.consensusj.supernautfx.SupernautFxLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;

/**
 * A simple Supernaut FX App implementing SupernautFxApp.
 */
@Singleton
public class HelloSupernautFxApp implements SupernautFxApp {
    private static final Logger log = LoggerFactory.getLogger(HelloSupernautFxApp.class);
    private final FxmlLoaderFactory loaderFactory;


    public static void main(String[] args) {
        SupernautFxLauncher.superLaunch(HelloSupernautFxApp.class, args);
    }

    public HelloSupernautFxApp(FxmlLoaderFactory loaderFactory) {
        this.loaderFactory = loaderFactory;
    }

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = loaderFactory.get(getFXMLUrl("MainWindow.fxml"));
        log.debug("primaryStage root FXML: {}", loader.getLocation());
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setTitle("SupernautFX Hello");
        primaryStage.show();
    }

    @Override
    public void stop() {
    }

    private URL getFXMLUrl(String fileName) {
        return HelloSupernautFxApp.class.getResource(fileName);
    }

}
