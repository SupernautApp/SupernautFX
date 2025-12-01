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
package app.supernaut.fx.micronaut;

import app.supernaut.BackgroundApp;
import app.supernaut.fx.ApplicationDelegate;
import app.supernaut.fx.FxLauncherProvider;
import app.supernaut.fx.fxml.FxmlLoaderFactory;
import app.supernaut.fx.micronaut.fxml.MicronautFxmlLoaderFactory;
import app.supernaut.fx.services.FxBrowserService;
import app.supernaut.fx.test.NoopBackgroundApp;
import app.supernaut.services.BrowserService;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.BeanContext;
import io.micronaut.context.env.Environment;
import javafx.application.Application;
import javafx.application.HostServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 *
 */
public class MicronautFxAppFactory implements FxLauncherProvider.AppFactory {
    private static final Logger log = LoggerFactory.getLogger(MicronautFxAppFactory.class);
    private final BeanContext context;

    /**
     * Constructor for Micronaut implementation of AppFactory
     * @param useApplicationContext create {@link ApplicationContext} if true, {@link BeanContext} if false
     */
    public MicronautFxAppFactory(boolean useApplicationContext) {
        if (useApplicationContext) {
            log.info("Creating Micronaut ApplicationContext");
            this.context = ApplicationContext.builder(Environment.CLI).build();
        } else {
            log.info("Creating Micronaut BeanContext");
            this.context = BeanContext.build();
        }

        log.info("Starting context");
        context.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BackgroundApp createBackgroundApp() {
        return context.findBean(BackgroundApp.class).orElse(new NoopBackgroundApp());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationDelegate createAppDelegate(Application proxyApplication) {
        log.info("getForegroundApp()");
        initializeBeanContext(context, proxyApplication);
        return context.getBean(ApplicationDelegate.class);
    }

    /**
     * Subclass {@link MicronautFxAppFactory} and override this method to customize your {@link BeanContext}.
     *
     * @param context The Micronaut BeanContext to initialize
     * @param proxyApplication The proxy implementation instance of {@link Application}
     */
    protected void initializeBeanContext(BeanContext context, Application proxyApplication) {
        log.info("initializeBeanContext()");
        // An app that wants access to the Application object can have it injected.
        context.registerSingleton(Application.class, proxyApplication);

        // An app that needs HostServices can have it injected. For opening URLs in browsers
        // the BrowserService interface is preferred.
        context.registerSingleton(HostServices.class, proxyApplication.getHostServices());
        context.registerSingleton(BrowserService.class, new FxBrowserService(proxyApplication.getHostServices()));

        // TODO: Make this dependency on FXML optional
        context.registerSingleton(FxmlLoaderFactory.class, new MicronautFxmlLoaderFactory(context));
    }
}
