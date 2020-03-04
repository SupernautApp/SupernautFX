package org.consensusj.supernautfx;

import java.net.URI;

/**
 * Injectable interface that abstracts HostServices.showDocument()
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
