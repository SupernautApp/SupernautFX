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
package app.supernaut.fx.micronaut;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.BeanContext;
import io.micronaut.context.env.Environment;
import javafx.application.Application;
import javafx.application.HostServices;
import app.supernaut.BackgroundApp;
import app.supernaut.services.BrowserService;
import app.supernaut.fx.SfxForegroundApp;
import app.supernaut.fx.SfxLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A launcher that uses http://micronaut.io to instantiate the foreground and background applications.
 */
public class MicronautSfxLauncher extends SfxLauncher {
    private static final Logger log = LoggerFactory.getLogger(SfxLauncher.class);

    /**
     *
     * @param initializeBackgroundAppOnNewThread If true, initializes {@code appFactorySupplier} and
     *        {@code BackgroundApp} on new thread, if false start them on calling thread (typically the main thread)
     */
    public MicronautSfxLauncher(boolean initializeBackgroundAppOnNewThread) {
        super(() -> new MicronautAppFactory(false), initializeBackgroundAppOnNewThread);
    }

    /**
     *
     * @param initializeBackgroundAppOnNewThread If true, initializes {@code appFactorySupplier} and
     *        {@code BackgroundApp} on new thread, if false start them on calling thread (typically the main thread)
     * @param useApplicationContext If {@code true} creates and uses an {@link ApplicationContext},
     *                             if {@code false} creates and uses a {@link BeanContext}
     */
    public MicronautSfxLauncher(boolean initializeBackgroundAppOnNewThread,
                                boolean useApplicationContext) {
        super(() -> new MicronautAppFactory(useApplicationContext), initializeBackgroundAppOnNewThread);
    }

    public static class MicronautAppFactory implements AppFactory {
        private final BeanContext context;

        public MicronautAppFactory(boolean useApplicationContext) {
            if (useApplicationContext) {
                log.info("Creating Micronaut ApplicationContext");
                this.context = ApplicationContext.build(Environment.CLI).build();
            } else {
                log.info("Creating Micronaut BeanContext");
                this.context = BeanContext.build();
            }

            log.info("Starting context");
            context.start();
        }

        @Override
        public BackgroundApp createBackgroundApp(Class<? extends BackgroundApp> backgroundAppClass) {
            return context.getBean(backgroundAppClass);
        }

        @Override
        public SfxForegroundApp createForegroundApp(Class<? extends SfxForegroundApp> foregroundAppClass, Application proxyApplication) {
            return getForegroundAppBean(foregroundAppClass, proxyApplication);
        }

        protected SfxForegroundApp getForegroundAppBean(Class<? extends SfxForegroundApp> clazz, Application proxyApplication) {
            log.info("getForegroundAppBean()");
            // Since SupernautFXApp doesn't extend Application, an app that needs access to the
            // Application object can have it injected.
            context.registerSingleton(Application.class, proxyApplication);

            // An app that needs HostServices can have it injected. For opening URLs in browsers
            // the BrowserService class is preferred.
            context.registerSingleton(HostServices.class, proxyApplication.getHostServices());
            context.registerSingleton(BrowserService.class, new SfxBrowserService(proxyApplication.getHostServices()));

            context.registerSingleton(SfxFxmlLoaderFactory.class, new SfxFxmlLoaderFactory(context));
            return context.getBean(clazz);
        }
    }
}
