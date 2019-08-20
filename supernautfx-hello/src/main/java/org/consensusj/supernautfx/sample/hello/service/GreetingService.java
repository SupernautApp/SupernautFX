package org.consensusj.supernautfx.sample.hello.service;

import javax.inject.Singleton;

/**
 * A demo, dependency injected service
 */
@Singleton
public class GreetingService {
    private final String name;

    public GreetingService(GreetingConfig config) {
        this.name = config.getGreeted();
    }

    public String greeting() {
        return "Hello " + name + "!";
    }
}
