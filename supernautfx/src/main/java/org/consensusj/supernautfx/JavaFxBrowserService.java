package org.consensusj.supernautfx;

import javafx.application.HostServices;

/**
 * Default implementation of BrowserService using JavaFX HostServices.
 */
public class JavaFxBrowserService implements BrowserService {
    private final HostServices hostServices;

    public JavaFxBrowserService(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    @Override
    public void showDocument(String uri) {
        hostServices.showDocument(uri);
    }
}
