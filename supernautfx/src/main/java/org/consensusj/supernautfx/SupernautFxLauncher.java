package org.consensusj.supernautfx;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * SupernautFX app launcher.
 * We subclass javafx.application.Application so you don't have to. To create a SupernautFX app,
 * write a class that implements SupernautFxApp.
 *
 */
public class SupernautFxLauncher extends Application {
    private static Class<? extends SupernautFxApp> mainClass;
    private ApplicationContext ctx;
    private SupernautFxApp app;
    private FxmlLoaderFactory loaderFactory;

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

    @Override
    public void init() throws Exception {
        ctx = ApplicationContext.build()
                //.mainClass(mainClass)
                .environments(Environment.CLI).build();
        loaderFactory = new FxmlLoaderFactory(ctx);
        ctx.registerSingleton(FxmlLoaderFactory.class, loaderFactory);
        ctx.start();
        app = ctx.getBean(mainClass);
        app.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        app.start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        app.stop();
        if(ctx != null && ctx.isRunning()) {
            ctx.stop();
        }
    }
}
