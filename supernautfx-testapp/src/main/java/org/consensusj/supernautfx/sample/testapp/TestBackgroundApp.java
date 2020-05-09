package org.consensusj.supernautfx.sample.testapp;

import io.micronaut.context.annotation.Context;
import org.consensusj.supernaut.BackgroundApp;

import javax.inject.Singleton;

/**
 *
 */
@Singleton
@Context
public class TestBackgroundApp implements BackgroundApp {

    @Override
    public void init()
    {
        TestApp.measurements.add("Background app inited");
    }

    @Override
    public void start() {
        TestApp.measurements.add("Background app started");
    }

    @Override
    public void stop() {
    }
}
