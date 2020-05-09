package org.consensusj.supernautfx

import org.consensusj.supernaut.BackgroundApp
import org.consensusj.supernaut.ForegroundApp
import org.consensusj.supernaut.Launcher
import org.consensusj.supernautfx.sample.SimpleJfxLauncher
import org.consensusj.supernautfx.test.NoopBackgroundApp
import org.consensusj.supernautfx.test.NoopSfxForegroundApp
import spock.lang.Ignore
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

/**
 *
 */
@Ignore("Can only run one integration test that starts a JFX Application per JVM instance")
class SimpleJfxLauncherIntegrationSpec extends Specification {
    def "Can launch and stop an app with background start"() {
        when:
        Launcher launcher =  new SimpleJfxLauncher(NoopBackgroundApp.class,
                NoopSfxForegroundApp.class,
                true);
        CompletableFuture<ForegroundApp> futureForegroundApp = launcher.launchAsync("")

        then:
        futureForegroundApp != null

        when:
        ForegroundApp foregroundApp = futureForegroundApp.get()
        CompletableFuture<BackgroundApp> futureBackgroundApp = launcher.getBackgroundApp();

        then:
        futureBackgroundApp != null
        foregroundApp != null
        foregroundApp instanceof ForegroundApp
        foregroundApp instanceof NoopSfxForegroundApp

        when:
        BackgroundApp backgroundApp = futureBackgroundApp.get()

        then:
        backgroundApp != null
        backgroundApp instanceof BackgroundApp
        backgroundApp instanceof NoopBackgroundApp

        when:
        foregroundApp.stop()
        backgroundApp.stop()

        then:
        true
    }
}
