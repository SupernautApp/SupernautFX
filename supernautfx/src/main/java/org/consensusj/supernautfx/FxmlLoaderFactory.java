package org.consensusj.supernautfx;

import io.micronaut.context.BeanContext;
import javafx.fxml.FXMLLoader;

import javax.inject.Singleton;
import java.net.URL;

/**
 * Factory for providing FXMLLoaders that do full DI
 */
@Singleton
public class FxmlLoaderFactory  {
    BeanContext context;

    public FxmlLoaderFactory(BeanContext context) {
        this.context = context;
    }

    public <T> T getControllerFactory(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public FXMLLoader get() {
        return get(null);
    }

    public FXMLLoader get(URL location) {
        FXMLLoader loader = new FXMLLoader(location);
        loader.setControllerFactory(this::getControllerFactory);
        return loader;
    }

}
