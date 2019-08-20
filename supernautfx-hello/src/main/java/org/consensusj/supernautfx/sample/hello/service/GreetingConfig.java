package org.consensusj.supernautfx.sample.hello.service;

import javax.inject.Singleton;

/**
 * Bean Factory
 */
@Singleton
public class GreetingConfig {
    public String getGreeted() {
        return "Mars";
    }
}
