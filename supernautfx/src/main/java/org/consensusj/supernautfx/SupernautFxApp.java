package org.consensusj.supernautfx;

import javafx.stage.Stage;

/**
 * Interface for implementing SupernautFX Apps.
 * Implement this interface instead of subclassing JavaFX `Application`.
 * You're now free use and abuse inheritance as you please. You also
 * can get your constructor injected, which is always fun.
 */
public interface SupernautFxApp extends AutoCloseable {

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
    default void init() throws Exception {
    }

    /**
     * The main entry point for SupernautFX applications. Called from the JavaFX
     * start() method.  At a minimum, you must implement this method.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws java.lang.Exception if something goes wrong
     */
    void start(Stage primaryStage) throws Exception;


    /**
     * This method is called when the application should stop, and provides a
     * convenient place to prepare for application exit and destroy resources.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     * @throws java.lang.Exception if something goes wrong
     */
    default void stop() throws Exception {
    }

    /**
     * For auto-close, just call stop.
     * @throws Exception if something goes wrong
     */
    default void close() throws Exception {
        stop();
    }
}
