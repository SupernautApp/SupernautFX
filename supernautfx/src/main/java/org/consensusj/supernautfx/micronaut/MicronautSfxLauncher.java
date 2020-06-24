package org.consensusj.supernautfx.micronaut;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.BeanContext;
import io.micronaut.context.env.Environment;
import javafx.application.Application;
import javafx.application.HostServices;
import org.consensusj.supernaut.BackgroundApp;
import org.consensusj.supernaut.services.BrowserService;
import org.consensusj.supernautfx.SfxForegroundApp;
import org.consensusj.supernautfx.SfxLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A launcher that uses http://micronaut.io to instantiate the foreground and background applications.
 */
public class MicronautSfxLauncher extends SfxLauncher {
    private static final Logger log = LoggerFactory.getLogger(SfxLauncher.class);


    /**
     *
     * @param backgroundAppClass The class object of the background app
     * @param foregroundAppClass The class object of the foreground app
     * @param initializeBackgroundAppOnNewThread If true, initializes {@code appFactorySupplier} and
     *        {@code BackgroundApp} on new thread, if false start them on calling thread (typically the main thread)
     */
    public MicronautSfxLauncher(Class<? extends BackgroundApp> backgroundAppClass,
                                Class<? extends SfxForegroundApp> foregroundAppClass,
                                boolean initializeBackgroundAppOnNewThread) {
        super(() -> new MicronautAppFactory(backgroundAppClass, foregroundAppClass,  false), initializeBackgroundAppOnNewThread);
    }

    /**
     *
     * @param backgroundAppClass The class object of the background app
     * @param foregroundAppClass The class object of the foreground app
     * @param initializeBackgroundAppOnNewThread If true, initializes {@code appFactorySupplier} and
     *        {@code BackgroundApp} on new thread, if false start them on calling thread (typically the main thread)
     * @param useApplicationContext If {@code true} creates and uses an {@link ApplicationContext},
     *                             if {@code false} creates and uses a {@link BeanContext}
     */
    public MicronautSfxLauncher(Class<? extends BackgroundApp> backgroundAppClass,
                                Class<? extends SfxForegroundApp> foregroundAppClass,
                                boolean initializeBackgroundAppOnNewThread,
                                boolean useApplicationContext) {
        super(() -> new MicronautAppFactory(backgroundAppClass, foregroundAppClass, useApplicationContext), initializeBackgroundAppOnNewThread);
    }

    public static class MicronautAppFactory implements AppFactory {
        private final Class<? extends SfxForegroundApp> foregroundAppClass;
        private final Class<? extends BackgroundApp> backgroundAppClass;
        private final BeanContext context;

        public MicronautAppFactory(Class<? extends BackgroundApp> backgroundAppClass,
                                   Class<? extends SfxForegroundApp> foregroundAppClass,
                                   boolean useApplicationContext) {
            this.foregroundAppClass = foregroundAppClass;
            this.backgroundAppClass = backgroundAppClass;
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
        public BackgroundApp createBackgroundApp() {
            return context.getBean(backgroundAppClass);
        }

        @Override
        public SfxForegroundApp createForegroundApp(Application proxyApplication) {
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
