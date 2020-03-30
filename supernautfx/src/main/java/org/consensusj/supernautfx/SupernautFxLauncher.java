package org.consensusj.supernautfx;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;


/**
 * SupernautFX application launcher
 * We subclass javafx.application.Application so you don't have to. To create a SupernautFX app,
 * write a class that implements SupernautFxApp.
 *
 */
public class SupernautFxLauncher extends Application {
    private static Class<? extends SupernautFxApp> mainClass;
    private ApplicationContext context;
    private SupernautFxApp app;

    public SupernautFxLauncher() {
    }
    
    /**
     * Use this static method to start your SupernautFX application
     *
     * @param mainClass The main class for your app, an injectable bean
     * @param args The args from your main routine
     */
    public static void superLaunch(Class<? extends SupernautFxApp> mainClass, String[] args) {
        SupernautFxLauncher.mainClass = mainClass;
        Application.launch(args);
    }

    /**
     * SupernautFX implementation of Application#init().
     * Initializes the ApplicationContext and loads and dependency injects the Application singleton.
     * @throws Exception if something goes wrong
     */
    @Override
    public void init() throws Exception {
        context = ApplicationContext.build()
                .environments(Environment.CLI)
                .build();

        initApplicationContext(context);

        context.start();
        app = context.getBean(mainClass);
        app.init();
    }

    /**
     * Initialize the ApplicationContext
     * Put a some JavaFX application-related beans in the context so apps and controllers
     * can request to have them injected.
     *
     * @param context The Micronaut application context
     */
    public void initApplicationContext(ApplicationContext context) {
        // Since SupernautFXApp doesn't extend Application, an app that needs access to the
        // Application object can have it injected.
        context.registerSingleton(Application.class, this);

        // An app that needs HostServices can have it injected. For opening URLs in browsers
        // the BrowserService class is preferred.
        context.registerSingleton(HostServices.class, this.getHostServices());
        context.registerSingleton(BrowserService.class, new JavaFxBrowserService(this.getHostServices()));
        
        context.registerSingleton(FxmlLoaderFactory.class, new FxmlLoaderFactory(context));
    }

    /**
     * SupernautFX implementation of Application#start().
     * Calls the applications implementation of SupernautFxApp#start
     *
     * @param primaryStage The primary Stage for the application
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        app.start(primaryStage);
    }


    /**
     * SupernautFX implementation of Application#stop().
     * Stops the SupernautFxApp and then stops the Micronaut ApplicationContext
     * @throws Exception if something goes wrong
     */
    @Override
    public void stop() throws Exception {
        app.stop();
        if(context != null && context.isRunning()) {
            context.stop();
        }
    }
}
