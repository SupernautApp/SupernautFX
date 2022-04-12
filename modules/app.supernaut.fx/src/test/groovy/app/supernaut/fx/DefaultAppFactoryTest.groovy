/*
 * Copyright 2019-2022 M. Sean Gilligan.
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
package app.supernaut.fx

import app.supernaut.fx.test.NoopBackgroundApp
import spock.lang.Specification

/**
 * Simple test of SfxLauncher.DefaultAppFactory
 */
class DefaultAppFactoryTest extends Specification {

    def "Can create a DefaultAppFactory"() {
        when:
        def factory = new FxLauncherAbstract.DefaultAppFactory()

        then:
        factory != null

        when:
        def backgroundApp = factory.createBackgroundApp(NoopBackgroundApp.class)

        then:
        backgroundApp != null
    }
}
