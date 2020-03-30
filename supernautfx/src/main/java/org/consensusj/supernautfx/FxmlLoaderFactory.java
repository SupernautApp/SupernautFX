package org.consensusj.supernautfx;

import io.micronaut.context.BeanContext;
import javafx.fxml.FXMLLoader;

import javax.inject.Singleton;
import java.net.URL;

/**
 * Factory for providing FXMLLoaders that do full DI. This
 * singleton is added to the Micronaut ApplicationContext with ApplicationContext#registerSingleton.
 */
@Singleton
public class FxmlLoaderFactory  {
    BeanContext context;

    /**
     * Constructor that gets ApplicationContext of the SupernautFX application injected.
     *
     * @param context The ApplicationContext of the SupernautFX application
     */
    public FxmlLoaderFactory(BeanContext context) {
        this.context = context;
    }

    /**
     * Get the FXML controller from the BeanContext
     *
     * @param clazz The controller class we are looking for
     * @param <T> The class type of the controller
     * @return A controller instance
     */
    public <T> T getControllerFactory(Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * Get an FXMLLoader without setting a location
     *
     * @return An FXMLLoader
     */
    public FXMLLoader get() {
        return get(null);
    }

    /**
     * Get an FXMLLoader for the given location
     *
     * @param location The location of the FXML resource
     * @return An FXMLLoader
     */
    public FXMLLoader get(URL location) {
        FXMLLoader loader = new FXMLLoader(location);
        loader.setControllerFactory(this::getControllerFactory);
        return loader;
    }

}
