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
package app.supernaut.fx;

import javafx.fxml.FXMLLoader;

import java.net.URL;

/**
 * Factory interface for providing DI-enabled instances of {@link FXMLLoader}
 */
public interface FxmlLoaderFactory {
    /**
     * Get the FXML controller (from a DI context)
     *
     * @param clazz The controller class we are looking for
     * @param <T> The class type of the controller
     * @return A controller instance
     */
    <T> T getControllerFactory(Class<T> clazz);

    /**
     * Get an FXMLLoader without setting a location
     *
     * @return An FXMLLoader
     */
    FXMLLoader get();

    /**
     * Get an FXMLLoader for the given location
     *
     * @param location The location of the FXML resource
     * @return An FXMLLoader
     */
    FXMLLoader get(URL location);

}
