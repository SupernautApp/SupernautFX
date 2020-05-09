package org.consensusj.supernautfx.test;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.consensusj.supernautfx.SfxForegroundApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * No-op ForegroundApp which exits immediately (used for testing)
 */
@Singleton
public final class NoopSfxForegroundApp implements SfxForegroundApp {
    private static final Logger log = LoggerFactory.getLogger(NoopSfxForegroundApp.class);

    @Override
    public void start(SfxMainView mainView) {
        log.info("Entered");
        mainView.show();
        noop();
    }
    
    private void noop() {
        log.info("Calling Plaform.exit()");
        Platform.exit();    // Exit OpenJFX
    }
}
