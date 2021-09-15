/*
 * Copyright 2019-2021 M. Sean Gilligan.
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
package app.supernaut.fx.micronaut.test;

import app.supernaut.fx.FxForegroundApp;
import app.supernaut.fx.FxmlLoaderFactory;

import jakarta.inject.Singleton;
import javafx.stage.Stage;

/**
 * FxForegroundApp with constructor that expects a {@link FxmlLoaderFactory}
 */
@Singleton
public class BaseFxmlForegroundApp implements FxForegroundApp {
    /** {@link FxmlLoaderFactory} for subclass access */
    protected final FxmlLoaderFactory fxmlLoaderFactory;

    /**
     * Constructor that gets an {@link FxmlLoaderFactory} injected
     *
     * @param fxmlLoaderFactory the injected factory
     */
    public BaseFxmlForegroundApp(FxmlLoaderFactory fxmlLoaderFactory) {
        this.fxmlLoaderFactory = fxmlLoaderFactory;
    }

    /**
     * No-op start method (can be overridden)
     * 
     * @param mainView A wrapper view containing the primary {@link Stage}
     * @throws Exception An exception occurred
     */
    @Override
    public void start(FxMainView mainView) throws Exception {
    }

    /**
     * Accessor for injected {@link FxmlLoaderFactory}
     * @return the injected factory
     */
    public FxmlLoaderFactory getFxmlLoaderFactory() {
        return fxmlLoaderFactory;
    }
}
