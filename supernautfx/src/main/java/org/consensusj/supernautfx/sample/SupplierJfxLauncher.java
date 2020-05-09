package org.consensusj.supernautfx.sample;

import org.consensusj.supernaut.BackgroundApp;
import org.consensusj.supernautfx.SfxLauncher;
import org.consensusj.supernautfx.SfxForegroundApp;

import java.util.function.Supplier;

/**
 * Demonstration Launcher that uses LambdaAppFactory.
 */
public class SupplierJfxLauncher extends SfxLauncher {

    public SupplierJfxLauncher(Supplier<BackgroundApp> backgroundAppSupplier,
                               Supplier<SfxForegroundApp> foregroundAppSupplier,
                               boolean backgroundStart) {
        super(() -> new LambdaAppFactory(backgroundAppSupplier, foregroundAppSupplier), backgroundStart);
    }
}
