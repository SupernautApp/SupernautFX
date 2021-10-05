/*
 * Copyright 2019-2021 M. Sean Gilligan.
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
package app.supernaut.fx.micronaut;

import app.supernaut.fx.FxmlLoaderFactory;
import io.micronaut.context.BeanContext;
import javafx.fxml.FXMLLoader;

import java.net.URL;

/**
 * Factory for providing FXMLLoaders that do full DI. This
 * singleton is added to the Micronaut BeanContext with BeanContext#registerSingleton.
 */
public class MicronautFxmlLoaderFactory implements FxmlLoaderFactory {
    BeanContext context;

    /**
     * Constructor that gets BeanContext of the SupernautFX application injected.
     *
     * @param context The BeanContext of the SupernautFX application
     */
    public MicronautFxmlLoaderFactory(BeanContext context) {
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
