package org.consensusj.supernautfx.test;

import org.consensusj.supernaut.BackgroundApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * A Noop background app for testing
 */
@Singleton
public class NoopBackgroundApp implements BackgroundApp {
    private static final Logger log = LoggerFactory.getLogger(NoopBackgroundApp.class);

    @Override
    public void start() {
        log.info("Start");
    }

    @Override
    public void stop() {
        log.info("Stop");
    }
}
