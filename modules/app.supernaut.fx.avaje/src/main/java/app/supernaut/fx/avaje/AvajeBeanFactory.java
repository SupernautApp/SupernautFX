/*
 * Copyright 2019-2022 M. Sean Gilligan.
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
package app.supernaut.fx.avaje;

import app.supernaut.fx.services.FxBrowserService;
import app.supernaut.services.BrowserService;
import io.avaje.inject.Factory;
import io.avaje.inject.Lazy;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import javafx.application.Application;

/**
 *
 */
@Lazy
@Singleton
@Factory
public class AvajeBeanFactory implements Provider<BrowserService> {
    public static Application proxyApplication;

    @Override
    public BrowserService get() {
        return new FxBrowserService(proxyApplication.getHostServices());
    }
}
