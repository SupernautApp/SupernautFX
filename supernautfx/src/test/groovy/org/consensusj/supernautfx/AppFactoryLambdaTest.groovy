package org.consensusj.supernautfx

import org.consensusj.supernautfx.test.NoopBackgroundApp
import org.consensusj.supernautfx.test.NoopSfxForegroundApp
import spock.lang.Specification

/**
 * Simple test of AppFactoryJfxLauncher.LambdaAppFactory
 */
class AppFactoryLambdaTest extends Specification {

    def "Can create an AppFactory with Lambdas"() {
        when:
        def factory = new SfxLauncher.LambdaAppFactory(
                {-> new NoopBackgroundApp()},
                {-> new NoopSfxForegroundApp() }
        )

        then:
        factory != null

        when:
        def backgroundApp = factory.createBackgroundApp()

        then:
        backgroundApp != null
    }
}
