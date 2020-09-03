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
package app.supernaut.fx;

import javafx.application.Application;
import app.supernaut.BackgroundApp;
import app.supernaut.ForegroundApp;
import app.supernaut.Launcher;
import app.supernaut.fx.internal.OpenJfxProxyApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

/**
 * JavaFX implementation of {@link Launcher}. This implementation provides the following functionality:
 * <ol>
 *     <li>
 *      Starts OpenJFX applications.
 *     </li>
 *     <li>
 *      Constructor provides an option to start {@link BackgroundApp} on a new thread (this allows
 *      the {@code BackgroundApp} and the OpenJFX {@link ForegroundApp} to initialize in parallel.)
 *     </li>
 *     <li>
 *      Implements {@link Launcher#launchAsync} which initializes the OpenJFX {@link ForegroundApp} on
 *      a new thread. This is not needed for a typical, packaged OpenJFX application
 *      which can just call {@link Launcher#launch} from its {@code static main()}, but is useful
 *      in various testing scenarios.
 *     </li>
 *     <li>
 *      Defines the {@link AppFactory} interface for constructing the {@link BackgroundApp} and {@link ForegroundApp}.
 *      This allows subclasses (or callers) to provide their own implementation of the application creation logic. The
 *      AppFactory interface was designed to allow usage of Dependency Injection frameworks like <b>Micronaut</b>
 *      to create dependency-injected implementations of {@link ForegroundApp} and {@link BackgroundApp}. The {@link AppFactory AppFactory}
 *      interface was also designed to be lazily-instantiated so the {@link AppFactory AppFactory} (dependency-injection framework)
 *      can initialize in parallel to OpenJFX.
 *     </li>
 *     <li>
 *      Uses the same {@link AppFactory AppFactory} (dependency-injection context) to initialize the Foreground and Background
 *      applications. A {@code CountDownLatch} is used to make sure the {@link AppFactory AppFactory} (which may be initialized
 *      on another thread along with the BackgroundApp) is ready when {@link OpenJfxProxyApplication} calls
 *      {@code createForegroundApp(Application proxyApplication)}.
 *     </li>
 * </ol>
 *
 */
public class SfxLauncher implements FxLauncher {
    private static final Logger log = LoggerFactory.getLogger(SfxLauncher.class);
    private static final String backgroundAppLauncherThreadName = "SupernautFX-Background-Launcher";
    private static final String foregroundAppLauncherThreadName = "SupernautFX-JavaFX-Launcher";

    private final boolean initializeBackgroundAppOnNewThread;
    private final Supplier<AppFactory> appFactorySupplier;
    private final CountDownLatch appFactoryInitializedLatch;
    private AppFactory appFactory;

    /* This future returns an initialized BackgroundApp */
    protected final CompletableFuture<BackgroundApp> futureBackgroundApp = new CompletableFuture<>();
    /* This future returns an initialized ForegroundApp */
    protected final CompletableFuture<ForegroundApp> futureForegroundApp = new CompletableFuture<>();

    private Class<? extends SfxForegroundApp> foregroundAppClass;

    /**
     * Interface that can be used to create and pre-initialize {@link ForegroundApp} and {@link BackgroundApp}.
     * This interface can be implemented by subclasses (or direct callers of the constructor.) By "pre-initialize" we
     * mean call implementation-dependent methods prior to {@code init()} or {@code start()}.
     * This interface is designed to support using Dependency Injection frameworks like Micronaut, see
     * {@code MicronautSfxLauncher}.
     */
    public interface AppFactory {
        BackgroundApp   createBackgroundApp(Class<? extends BackgroundApp> backgroundAppClass);
        SfxForegroundApp createForegroundApp(Class<? extends SfxForegroundApp> foregroundAppClass, Application proxyApplication);
    }

    /**
     * Default implementation of AppFactory.
     */
    public static class DefaultAppFactory implements AppFactory {
        
        @Override
        public BackgroundApp createBackgroundApp(Class<? extends BackgroundApp> backgroundAppClass) {
            return newInstance(backgroundAppClass);
        }

        @Override
        public SfxForegroundApp createForegroundApp(Class<? extends SfxForegroundApp> foregroundAppClass, Application proxyApplication) {
            return newInstance(foregroundAppClass);
        }

        /**
         * newInstance without checked exceptions.
         *
         * @param clazz A Class object that must have a no-args constructor.
         * @param <T> The type of the class
         * @return A new instanceof the class
         * @throws RuntimeException exceptions thrown by {@code newInstance()}.
         */
        private static <T> T newInstance(Class<T> clazz) {
            T foregroundApp;
            try {
                foregroundApp = clazz.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            return foregroundApp;
        }
    }

    /**
     * Construct an Asynchronous Launcher that works with OpenJFX.
     * 
     * @param appFactorySupplier A Supplier that will lazily instantiate an AppFactory.
     * @param initializeBackgroundAppOnNewThread If true, initializes {@code appFactorySupplier} and
     *        {@code BackgroundApp} on new thread, if false start them on calling thread (typically the main thread)
     */
    public SfxLauncher(Supplier<AppFactory> appFactorySupplier, boolean initializeBackgroundAppOnNewThread) {
        this.appFactorySupplier = appFactorySupplier;
        this.initializeBackgroundAppOnNewThread = initializeBackgroundAppOnNewThread;
        appFactoryInitializedLatch = new CountDownLatch(1);
    }
    
    @Override
    public CompletableFuture<ForegroundApp> launchAsync(String[] args, Class<? extends ForegroundApp> foregroundAppClass, Class<? extends BackgroundApp> backgroundApp) {
        log.info("launchAsync...");
        launchInternal(args, foregroundAppClass, backgroundApp, true);
        return getForegroundApp();
    }

    @Override
    public void launch(String[] args, Class<? extends ForegroundApp> foregroundAppClass, Class<? extends BackgroundApp> backgroundApp) {
        log.info("launch...");
        launchInternal(args, foregroundAppClass, backgroundApp, false);
    }

    /**
     * Called by {@code OpenJfxProxyApplication} to create its delegate {@link SfxForegroundApp} object.
     * Waits on a {@link CountDownLatch} to make sure the {@link AppFactory AppFactory} is ready.
     * 
     * @param proxyApplication The calling instance of {@code OpenJfxProxyApplication}
     * @return The newly constructed OpenJFX-compatible {@link SfxForegroundApp}
     */
    @Override
    public SfxForegroundApp createForegroundApp(Application proxyApplication) {
        try {
            appFactoryInitializedLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        SfxForegroundApp foregroundApp = appFactory.createForegroundApp(foregroundAppClass, proxyApplication);
        if (foregroundApp instanceof SfxForegroundApp.OpenJfxApplicationAware) {
            // If foregroundApp implements the interface, pass it the JfxApplication implementation
            ((SfxForegroundApp.OpenJfxApplicationAware) foregroundApp).setJfxApplication(proxyApplication);
        }
        // TODO: Create a LauncherAware interface for injecting the launcher into apps?
        futureForegroundApp.complete(foregroundApp);
        return foregroundApp;
    }

    @Override
    public CompletableFuture<ForegroundApp> getForegroundApp() {
        return futureForegroundApp;
    }

    @Override
    public CompletableFuture<BackgroundApp> getBackgroundApp() {
        return futureBackgroundApp;
    }

    /**
     * Internal launch method called by both {@code launchAsync()} and {@code launch()}
     * @param args Command-line arguments to pass to the Foreground (OpenJFX) application
     * @param initForegroundOnNewThread If true, start OpenJFX on a new thread, if false start it on
     *                        calling thead (typically this will be the main thread)
     */
    private void launchInternal(String[] args, Class<? extends ForegroundApp> foregroundAppClass, Class<? extends BackgroundApp> backgroundAppClass, boolean initForegroundOnNewThread) {
        Class<? extends SfxForegroundApp> sfxForegroundAppClass = (Class<? extends SfxForegroundApp>) foregroundAppClass;
        launchBackgroundApp(backgroundAppClass);
        launchForegroundApp(args, sfxForegroundAppClass, initForegroundOnNewThread);
    }

    private void launchBackgroundApp(Class<? extends BackgroundApp> backgroundAppClass) {
        if (initializeBackgroundAppOnNewThread) {
            log.info("Launching background app on {} thread", backgroundAppLauncherThreadName);
            startThread(backgroundAppLauncherThreadName,  () -> startBackgroundApp(backgroundAppClass));
        } else {
            log.info("Launching background app on caller's thread");
            startBackgroundApp(backgroundAppClass);
        }
    }

    private void launchForegroundApp(String[] args, Class<? extends SfxForegroundApp> foregroundAppClass, boolean async) {
        if (async) {
            log.info("Launching on {} thread", foregroundAppLauncherThreadName);
            startThread(foregroundAppLauncherThreadName, () -> startForegroundApp(args, foregroundAppClass));
        } else {
            log.info("Launching on caller's thread");
            startForegroundApp(args, foregroundAppClass);
        }
    }

    private void startBackgroundApp(Class<? extends BackgroundApp> backgroundAppClass) {
        log.info("Instantiating appFactory");
        this.appFactory = appFactorySupplier.get();

        /*
         * Tell the foreground app thread that appFactory is initialized.
         */
        log.info("Release appFactoryInitializedLatch");
        appFactoryInitializedLatch.countDown();

        log.info("Instantiating backgroundApp class");
        BackgroundApp backgroundApp = appFactory.createBackgroundApp(backgroundAppClass);

        /*
         * Do any (hopefully minimal) background initialization that
         * is needed before starting the foreground
         */
        log.info("Init backgroundApp");
        backgroundApp.init();

        futureBackgroundApp.complete(backgroundApp);


        /*
         * Call the background app, so it can start its own threads
         */
        backgroundApp.start();
    }
    
    private void startForegroundApp(String[] args, Class<? extends SfxForegroundApp> foregroundAppClass) {
        OpenJfxProxyApplication.configuredLauncher = this;
        this.foregroundAppClass = foregroundAppClass;
        log.info("Calling Application.launch()");
        Application.launch(OpenJfxProxyApplication.class, args);
        log.info("OpenJfxProxyApplication exited.");
    }

    private Thread startThread(String threadName, Runnable target) {
        Thread thread = new Thread(target);
        thread.setName(threadName);
        thread.start();
        return thread;
    }
}
