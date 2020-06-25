/*
 * Copyright 2019-2020 M. Sean Gilligan.
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
package org.consensusj.supernaut.services;

import java.net.URI;

/**
 * Interface that abstracts JavaFX {@code }HostServices.showDocument()}
 * Use this interface to controllers/services that wish to tell a browser
 * to open a window. The interface allows you to not reference JavaFX
 * in your controller and to use mocks/stubs for unit test, etc.
 */
public interface BrowserService {
    /**
     * Opens the specified URI in a new browser window or tab.
     *
     * @param uri the URI of the web page that will be opened in a browser.
     */
    void showDocument(String uri);

    /**
     * Convenience method that takes URI
     *
     * @param uri the URI of the web page that will be opened in a browser
     */
    default void showDocument(URI uri) {
        showDocument(uri.toString());
    }
}
