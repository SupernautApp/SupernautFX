package org.consensusj.supernautfx.sample.testapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.consensusj.supernaut.BackgroundApp;
import org.consensusj.supernaut.ForegroundApp;
import org.consensusj.supernaut.logging.JavaLoggingSupport;
import org.consensusj.supernautfx.micronaut.SfxFxmlLoaderFactory;
import org.consensusj.supernautfx.SfxForegroundApp;
import org.consensusj.supernautfx.micronaut.MicronautSfxLauncher;
import org.consensusj.supernaut.test.TimingMeasurements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A simple Supernaut FX App implementing SupernautFxApp.
 */
@Singleton
public class TestApp implements SfxForegroundApp, SfxForegroundApp.OpenJfxApplicationAware {
    private static final Logger log = LoggerFactory.getLogger(TestApp.class);
    private static boolean backgroundStart = true;
    private final SfxFxmlLoaderFactory loaderFactory;
    private Application fxApplication;
    private String testParam;
    final static TimingMeasurements measurements = new TimingMeasurements();


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        measurements.add("Entered main()");
        JavaLoggingSupport.configure(TestApp.class, "org.consensusj.supernautfx.sample.testapp");

        if (args.length > 1 && args[0].equals("--sequential-launch")) {
            log.info("SEQUENTIAL LAUNCH");
            backgroundStart = false;
        }
        if (args.length > 1 && args[0].equals("--test=exit_main_begin")) {
            System.exit(0);
        }

        /*
           Get a Launcher. It should really come from a ServiceLoader
         */
        log.info("Entered main, getting launcher...");
        MicronautSfxLauncher launcher = getLauncher();

        /*
           Get a Future for a ForegroundApp app, then resolve the future
         */
        log.info("Calling launch");
        CompletableFuture<ForegroundApp> futureForegroundApp = launcher.launchAsync(args);
        log.info("We have a CompletableFuture<ForegroundApp>");

        ForegroundApp foregroundApp = futureForegroundApp.get();
        measurements.add("ForegroundApp app ready");

        /*
           Get a Future for a BackgroundApp app, then resolve the future
         */
        log.info("Getting a CompletableFuture<BackgroundApp>");
        CompletableFuture<BackgroundApp> futureBackgroundApp = launcher.getBackgroundApp();
        log.info("We have a CompletableFuture<BackgroundApp>");

        BackgroundApp backgroundApp = futureBackgroundApp.get();
        log.info("We have a BackgroundApp");

        if (args.length > 1 && args[0].equals("--test=exit_main_end")) {
            System.exit(0);
        }

    }

    private static MicronautSfxLauncher getLauncher() {
        return new MicronautSfxLauncher(TestBackgroundApp.class, TestApp.class, backgroundStart);
    }

    public TestApp(SfxFxmlLoaderFactory loaderFactory) {
        measurements.add("App constructed");
        log.info("Constructing TestApp");
        this.loaderFactory = loaderFactory;
    }

    @Override
    public void setJfxApplication(Application application) {
        this.fxApplication = application;
    }

    @Override
    public void init() {
        measurements.add("App inited");
        log.info("Initializing TestApp");
        var namedParms = fxApplication.getParameters().getNamed();
        testParam = namedParms.getOrDefault("test", "");
        if (testParam.equals("exit_init")) {
            // Minimal test, make sure the app an start and quit right away.
            stopExit();
        }
    }

    @Override
    public void start(SfxMainView mainView) throws IOException {
        Stage primaryStage = mainView.optionalStage().orElseThrow();
        measurements.add("App started");
        log.info("Starting TestApp");

        if (testParam.equals("exit_start_begin")) {
            stopExit();
        }

        FXMLLoader loader = loaderFactory.get(getFXMLUrl("MainWindow.fxml"));
        log.info("primaryStage root FXML: {}", loader.getLocation());
        Parent root = loader.load();
        log.info("FXML root loaded");
        measurements.add("FXML root loaded");

        primaryStage.setScene(new Scene(root, 300, 250));
        measurements.add("Scene set");

        primaryStage.setTitle("Supernaut/FX TestApp");
        primaryStage.show();
        measurements.add("primaryStage shown");
        if (testParam.equals("exit_start_end")) {
            stopExit();
        }
        if (testParam.equals("quit")) {
            stopPlatformExit();
        }
    }
    
    @Override
    public void stop() {
        log.info("Stopping TestApp");
        measurements.add("stopping");
        measurements.dump();
    }

    private void stopExit() {
        log.info("stopExit\n");
        measurements.dump();
        System.exit(0); // Use a little bit of brute force
    }

    private void stopPlatformExit() {
        log.info("stopPlatformExit\n");
        Platform.exit();
    }

    private URL getFXMLUrl(String fileName) {
        return TestApp.class.getResource(fileName);
    }
}
