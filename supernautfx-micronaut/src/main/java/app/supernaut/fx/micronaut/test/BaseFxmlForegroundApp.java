package app.supernaut.fx.micronaut.test;

import app.supernaut.fx.FxForegroundApp;
import app.supernaut.fx.FxmlLoaderFactory;

import javax.inject.Singleton;

/**
 * FxForegroundApp with constructor that expects a {@link FxmlLoaderFactory}
 */
@Singleton
public class BaseFxmlForegroundApp implements FxForegroundApp {
    protected final FxmlLoaderFactory fxmlLoaderFactory;

    public BaseFxmlForegroundApp(FxmlLoaderFactory fxmlLoaderFactory) {
        this.fxmlLoaderFactory = fxmlLoaderFactory;
    }

    @Override
    public void start(FxMainView mainView) throws Exception {

    }
}
