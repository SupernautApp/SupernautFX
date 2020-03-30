package org.consensusj.supernautfx;

import javafx.application.HostServices;

/**
 * Default implementation of BrowserService using JavaFX HostServices.
 */
public class JavaFxBrowserService implements BrowserService {
    private final HostServices hostServices;

    /**
     * Constructor
     * @param hostServices HostServices object to wrap
     */
    public JavaFxBrowserService(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    /**
     * Implementation of showDocument using HostServices
     * @param uri the URI of the web page that will be opened in a browser.
     */
    @Override
    public void showDocument(String uri) {
        hostServices.showDocument(uri);
    }
}
