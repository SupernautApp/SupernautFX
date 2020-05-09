package org.consensusj.supernautfx;

import javafx.application.Application;
import org.consensusj.supernaut.BackgroundApp;
import org.consensusj.supernaut.ForegroundApp;
import org.consensusj.supernaut.Launcher;
import org.consensusj.supernautfx.internal.OpenJfxProxyApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class SfxLauncher implements Launcher, OpenJfxProxyApplication.JfxLauncher {
    private static final Logger log = LoggerFactory.getLogger(SfxLauncher.class);
    private static final String backgroundAppLauncherThreadName = "SupernautFX-Background-Launcher";
    private static final String foregroundAppLauncherThreadName = "SupernautFX-JavaFX-Launcher";
    private static SfxLauncher INSTANCE;

    private final boolean initializeBackgroundAppOnNewThread;
    private final Supplier<AppFactory> appFactorySupplier;
    private final CountDownLatch appFactoryInitializedLatch;
    private AppFactory appFactory;

    /* This future returns an initialized BackgroundApp */
    protected final CompletableFuture<BackgroundApp> futureBackgroundApp = new CompletableFuture<>();
    /* This future returns an initialized ForegroundApp */
    protected final CompletableFuture<ForegroundApp> futureForegroundApp = new CompletableFuture<>();

    /**
     * Interface that can be used to create and pre-initialize {@link ForegroundApp} and {@link BackgroundApp}.
     * This interface can be implemented by subclasses (or direct callers of the constructor.) By "pre-initialize" we
     * mean call implementation-dependent methods prior to {@code init()} or {@code start()}.
     * This interface is designed to support using Dependency Injection frameworks like Micronaut, see
     * {@link org.consensusj.supernautfx.micronaut.MicronautSfxLauncher}.
     */
    public interface AppFactory {
        BackgroundApp   createBackgroundApp();
        SfxForegroundApp createForegroundApp(Application proxyApplication);
    }

    /**
     * Flexible, lambda-friendly implementation of AppFactory using {@link Supplier}.
     * Using {@link LambdaAppFactory LambdaAppFactory},
     * implementations can be as simple as:
     * <pre>{@code
     *          var factory = new AppFactoryJfxLauncher.LambdaAppFactory(
     *                 () -> new NoopBackgroundApp(),
     *                 () -> new NoopJfxForegroundApp());
     * }</pre>
     */
    public static class LambdaAppFactory implements AppFactory {
        private final Supplier<BackgroundApp> backgroundAppSupplier;
        private final Supplier<SfxForegroundApp> foregroundAppSupplier;

        public LambdaAppFactory(Supplier<BackgroundApp> backgroundAppSupplier,
                                  Supplier<SfxForegroundApp> foregroundAppSupplier) {
            this.backgroundAppSupplier = backgroundAppSupplier;
            this.foregroundAppSupplier = foregroundAppSupplier;
        }

        @Override
        public BackgroundApp createBackgroundApp() {
            return backgroundAppSupplier.get();
        }

        @Override
        public SfxForegroundApp createForegroundApp(Application proxyApplication) {
            return foregroundAppSupplier.get();
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
        INSTANCE = this;
        this.appFactorySupplier = appFactorySupplier;
        this.initializeBackgroundAppOnNewThread = initializeBackgroundAppOnNewThread;
        appFactoryInitializedLatch = new CountDownLatch(1);
    }

    /**
     * This is a temporary hack. Should be removed shortly.
     *
     * @return The one-and-only instance
     * @throws RuntimeException if called before the constructor is used.
     */
    @Deprecated
    public static SfxLauncher getInstance() throws RuntimeException {
        if (INSTANCE == null) throw new RuntimeException("getInstance() called when INSTANCE is null");
        return INSTANCE;
    }

    @Override
    public CompletableFuture<ForegroundApp> launchAsync(String[] args) {
        log.info("launchAsync...");
        launchInternal(args, true);
        return getForegroundApp();
    }

    @Override
    public void launch(String[] args) {
        log.info("launch...");
        launchInternal(args, false);
    }


    /**
     * Called by {@code OpenJfxProxyApplication} to create its delegate {@link SfxForegroundApp} object.
     * Waits on a {@link CountDownLatch} to make sure the {@link AppFactory AppFactory} is ready.
     * 
     * @param proxyApplication The calling instance of {@link OpenJfxProxyApplication}
     * @return The newly constructed OpenJFX-compatible {@link SfxForegroundApp}
     */
    @Override
    public SfxForegroundApp createForegroundApp(OpenJfxProxyApplication proxyApplication) {
        try {
            appFactoryInitializedLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        SfxForegroundApp foregroundApp = appFactory.createForegroundApp(proxyApplication);
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
    private void launchInternal(String[] args, boolean initForegroundOnNewThread) {
        launchBackgroundApp();
        launchForegroundApp(args, initForegroundOnNewThread);
    }

    private void launchBackgroundApp() {
        if (initializeBackgroundAppOnNewThread) {
            log.info("Launching background app on {} thread", backgroundAppLauncherThreadName);
            startThread(backgroundAppLauncherThreadName, this::startBackgroundApp);
        } else {
            log.info("Launching background app on caller's thread");
            startBackgroundApp();
        }
    }

    private void launchForegroundApp(String[] args, boolean async) {
        if (async) {
            log.info("Launching on {} thread", foregroundAppLauncherThreadName);
            startThread(foregroundAppLauncherThreadName, () -> startForegroundApp(args));
        } else {
            log.info("Launching on caller's thread");
            startForegroundApp(args);
        }
    }

    private void startBackgroundApp() {
        log.info("Instantiating appFactory");
        this.appFactory = appFactorySupplier.get();
        appFactoryInitializedLatch.countDown();

        log.info("Instantiating backgroundApp class");
        BackgroundApp backgroundApp = createBackgroundApp();

        /*
         * Do any (hopefully minimal) background initialization that
         * is needed before starting the foreground
         */
        log.info("Init backgroundApp");
        backgroundApp.init();

        futureBackgroundApp.complete(backgroundApp);

        /*
         * Tell the foreground app thread that background app is initialized.
         */
        log.info("Release contextInitializedLatch");

        /*
         * Call the background app, so it can start its own threads
         */
        backgroundApp.start();
    }

    private BackgroundApp createBackgroundApp() {
        return appFactory.createBackgroundApp();
    }
    

    private void startForegroundApp(String[] args) {
        log.info("Calling Application.launch()");
        Application.launch(OpenJfxProxyApplication.class, args);
        log.info("JfxApp/SkyApp exited.");
    }

    private Thread startThread(String threadName, Runnable target) {
        Thread thread = new Thread(target);
        thread.setName(threadName);
        thread.start();
        return thread;
    }
}
