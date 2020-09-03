package app.supernaut.fx;

import app.supernaut.Launcher;
import javafx.application.Application;

/**
 * Additional required method for launching {@link SfxForegroundApp} instances that
 * are proxied by {@code OpenJfxProxyApplication}.
 */
public interface FxLauncher extends Launcher {
    /**
     * Construct a {@link SfxForegroundApp} that is a delegate to {@code OpenJfxProxyApplication}.
     * @param jfxApplication The OpenJfx "proxy" app instance
     * @return A newly constructed (and possibly injected) foreground app
     */
    SfxForegroundApp createForegroundApp(Application jfxApplication);
}
