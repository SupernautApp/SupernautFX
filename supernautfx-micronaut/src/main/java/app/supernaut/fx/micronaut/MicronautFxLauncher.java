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

import app.supernaut.fx.FxmlLoaderFactory;
import app.supernaut.fx.services.FxBrowserService;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.BeanContext;
import io.micronaut.context.env.Environment;
import javafx.application.Application;
import javafx.application.HostServices;
import app.supernaut.BackgroundApp;
import app.supernaut.services.BrowserService;
import app.supernaut.fx.FxForegroundApp;
import app.supernaut.fx.FxLauncherAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A launcher that uses http://micronaut.io to instantiate the foreground and background applications.
 */
public class MicronautFxLauncher extends FxLauncherAbstract {
    private static final Logger log = LoggerFactory.getLogger(FxLauncherAbstract.class);

    public MicronautFxLauncher() {
        this(true);
    }

    /**
     *
     * @param initializeBackgroundAppOnNewThread If true, initializes {@code appFactorySupplier} and
     *        {@code BackgroundApp} on new thread, if false start them on calling thread (typically the main thread)
     */
    public MicronautFxLauncher(boolean initializeBackgroundAppOnNewThread) {
        super(() -> new MicronautAppFactory(false), initializeBackgroundAppOnNewThread);
    }

    /**
     *
     * @param initializeBackgroundAppOnNewThread If true, initializes {@code appFactorySupplier} and
     *        {@code BackgroundApp} on new thread, if false start them on calling thread (typically the main thread)
     * @param useApplicationContext If {@code true} creates and uses an {@link ApplicationContext},
     *                             if {@code false} creates and uses a {@link BeanContext}
     */
    public MicronautFxLauncher(boolean initializeBackgroundAppOnNewThread,
                               boolean useApplicationContext) {
        super(() -> new MicronautAppFactory(useApplicationContext), initializeBackgroundAppOnNewThread);
    }

    @Override
    public String name() {
        return "micronaut";
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
        public FxForegroundApp createForegroundApp(Class<? extends FxForegroundApp> foregroundAppClass, Application proxyApplication) {
            return getForegroundAppBean(foregroundAppClass, proxyApplication);
        }

        /**
         * Subclass {@link MicronautAppFactory} and override this method to customize your {@link BeanContext}.
         * <p>
         * @param clazz The FXForegroundApp sub-class that we are creating and injecting
         * @param proxyApplication The proxy implementation instance of {@link Application}
         * @return A newly constructed and injected {@link FxForegroundApp} instance
         */
        protected FxForegroundApp getForegroundAppBean(Class<? extends FxForegroundApp> clazz, Application proxyApplication) {
            log.info("getForegroundAppBean()");
            // Since FXForegroundApp doesn't extend Application, an app that needs access to the
            // Application object can have it injected.
            context.registerSingleton(Application.class, proxyApplication);

            // An app that needs HostServices can have it injected. For opening URLs in browsers
            // the BrowserService interface is preferred.
            context.registerSingleton(HostServices.class, proxyApplication.getHostServices());
            context.registerSingleton(BrowserService.class, new FxBrowserService(proxyApplication.getHostServices()));

            context.registerSingleton(FxmlLoaderFactory.class, new MicronautFxmlLoaderFactory(context));
            return context.getBean(clazz);
        }
    }
}
