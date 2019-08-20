package org.consensusj.supernautfx;

import javafx.stage.Stage;

/**
 * Interface for Supernaut Apps.
 * Implement this interface instead of subclassing JavaFX `Application`.
 * You're now free use and abuse inheritance as you please. You also
 * can get your constructor injected, which is always fun.
 */
public interface SupernautFxApp extends AutoCloseable {

    default void init() throws Exception {
    }
    void start(Stage primaryStage) throws Exception;
    default void stop() throws Exception {
    }
    default void close() throws Exception {
        stop();
    }
}
