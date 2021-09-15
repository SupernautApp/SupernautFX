/*
 * Copyright 2019-2021 M. Sean Gilligan.
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
package app.supernaut.fx.micronaut

import app.supernaut.BackgroundApp
import app.supernaut.ForegroundApp
import app.supernaut.Launcher
import app.supernaut.fx.test.NoopBackgroundApp
import app.supernaut.fx.test.NoopFxForegroundApp
import spock.lang.Ignore
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

/**
 *
 */
@Ignore("Can only run one integration test that starts a JFX Application per JVM instance")
class MicronautFxLauncherIntegrationSpec extends Specification{
    
    def "Can launch and stop an app with background start"() {
        when:
        Launcher launcher =  new MicronautFxLauncher(NoopBackgroundApp.class,
                            NoopFxForegroundApp.class,
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
        foregroundApp instanceof NoopFxForegroundApp

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
