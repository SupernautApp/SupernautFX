package org.consensusj.supernautfx.sample.hello.service;

import javax.inject.Named;
import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;

/**
 * A simple dependency-injected service
 */
@Singleton
public class GreetingService {
    private final String planetName;

    public GreetingService(@Named("PERSONNAME") String planetName) {
        this.planetName = planetName;
    }

    /**
     * Return the name of the planet being greeted
     *
     * @return The name of the planet being greeted
     */
    public String getPlanetName() {
        return this.planetName;
    }
    
    /**
     * Return a greeting
     *
     * @return The greeting
     */
    public String greeting() {
        String time = java.time.ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
        return "Hello " + planetName + "! The time is: " + time;
    }
}
