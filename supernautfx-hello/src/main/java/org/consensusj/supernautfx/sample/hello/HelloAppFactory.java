package org.consensusj.supernautfx.sample.hello;

import io.micronaut.context.annotation.Factory;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Micronaut Factory for our Hello application.
 * (It might be nice to abstract these factory beans so they are not
 * directly dependent on Micronaut, but at this point using the @Factory
 * annotation seems to be the simplest way to do things.)
 */
@Factory
public class HelloAppFactory {

    @Singleton
    @Named("PERSONNAME")
    public String getPlanetName() {
        return "Mars";
    }
}
