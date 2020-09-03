/*
 * Copyright 2019-2020 M. Sean Gilligan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.supernaut.fx.sample.hello;

import app.supernaut.fx.test.NoopBackgroundApp;
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

    @Singleton
    public NoopBackgroundApp getBackgroundApp() {
        return new NoopBackgroundApp();
    }
}
