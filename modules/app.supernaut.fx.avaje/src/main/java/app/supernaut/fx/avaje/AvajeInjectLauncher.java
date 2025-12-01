package app.supernaut.fx.avaje;

import app.supernaut.BackgroundApp;
import app.supernaut.fx.ApplicationDelegate;
import app.supernaut.fx.FxLauncherAbstract;
import app.supernaut.fx.test.NoopBackgroundApp;
import io.avaje.inject.BeanScope;
import javafx.application.Application;

import java.util.function.Supplier;

/**
 *
 */
public class AvajeInjectLauncher extends FxLauncherAbstract {
    /**
     * Construct an Asynchronous Launcher that works with OpenJFX.
     *
     * @param initializeBackgroundAppOnNewThread If true, initializes {@code appFactorySupplier} and
     *                                           {@code BackgroundApp} on new thread, if false start them on calling thread (typically the main thread)
     */
    public AvajeInjectLauncher(boolean initializeBackgroundAppOnNewThread) {
        super(AvajeInjectAppFactory::new, initializeBackgroundAppOnNewThread);
    }

    @Override
    public String name() {
        return "avaje-inject";
    }


    public static class AvajeInjectAppFactory implements AppFactory {
        private final BeanScope beanScope;

        public AvajeInjectAppFactory() {
            beanScope = BeanScope.builder().build();
        }

        @Override
        public BackgroundApp createBackgroundApp(Class<? extends BackgroundApp> backgroundAppClass) {
            if (backgroundAppClass.equals(NoopBackgroundApp.class)) {
                // Special case for NoopBackgroundApp which is not an (annotated) Bean
                return new NoopBackgroundApp();
            } else {
                throw new UnsupportedOperationException("Not implemented yet");
            }
        }

        @Override
        public ApplicationDelegate createAppDelegate(Class<? extends ApplicationDelegate> appDelegateClass, Application proxyApplication) {
            return beanScope.get(appDelegateClass);
        }
    }
}
