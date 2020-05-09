package org.consensusj.supernautfx.micronaut;

import io.micronaut.context.annotation.Context;
import javafx.application.HostServices;
import org.consensusj.supernaut.services.BrowserService;

import javax.inject.Singleton;

/**
 * Default implementation of BrowserService using JavaFX HostServices.
 */
@Singleton
public class SfxBrowserService implements BrowserService {
    private final HostServices hostServices;

    /**
     * Constructor
     * @param hostServices HostServices object to wrap
     */
    public SfxBrowserService(HostServices hostServices) {
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
