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

import app.supernaut.fx.FxmlLoaderFactory
import app.supernaut.fx.micronaut.test.BaseFxmlForegroundApp
import io.micronaut.context.BeanContext
import javafx.fxml.FXMLLoader
import spock.lang.Specification

/**
 *
 */
class FxmlLoaderFactoryIntegrationSpecification extends Specification {
    def "Can create, find, and use an FxmlLoaderFactory in BeanContext"() {
        given: "a BeanContext"
        BeanContext ctx = BeanContext.run()

        when: "We create and register a MicronautFxmlLoaderFactory"
        def loaderFactory = new MicronautFxmlLoaderFactory(ctx);
        ctx.registerSingleton(FxmlLoaderFactory.class, loaderFactory);

        and: "We retrieve it"
        FxmlLoaderFactory foundFactory = ctx.getBean(FxmlLoaderFactory.class);

        then: "It is successfully retrieved"
        foundFactory != null
        foundFactory instanceof MicronautFxmlLoaderFactory

        when: "We use the FxmlLoaderFactory"
        FXMLLoader loader = foundFactory.get();

        then: "It creates an FXMLLoader"
        loader != null
        loader instanceof FXMLLoader
    }

    def "Can create an FXMLLoader factory and inject into test application class"() {
        given: "a BeanContext with a FxmlLoaderFactory singleton"
        BeanContext ctx = BeanContext.run()
        def loaderFactory = new MicronautFxmlLoaderFactory(ctx)
        ctx.registerSingleton(FxmlLoaderFactory.class, loaderFactory)

        when: "We create an Application bean with a constructor that requires a FxmlLoaderFactory"
        BaseFxmlForegroundApp testApp = ctx.createBean(BaseFxmlForegroundApp.class)


        then: "The Application bean is successfully created and the FxmlLoaderFactory was injected. "
        testApp != null
        testApp.fxmlLoaderFactory == loaderFactory   // FXMLLoaderFactory was injected
    }
}
