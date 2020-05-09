package org.consensusj.supernaut;

/**
 *  An abstraction of a User Interface application that is separated
 *  into a foreground app and a background app. It is logically-compatible
 *  with JavaFX, an implementation using JavaFX is provided.
 */
public interface ForegroundApp {
    default void init() throws Exception {
    }

    void start(SupernautMainView view) throws Exception;

    default void stop() throws Exception {
    }

    /**
     * Marker interface for a view. In JavaFX this is a JavaFX "controller".
     */
    interface SupernautView {
    }

    /**
     * Marker interface for Main View. In JavaFX this is the view contained
     * in the primary {@code Stage}.
     */
    interface SupernautMainView extends SupernautView {
    }
}
