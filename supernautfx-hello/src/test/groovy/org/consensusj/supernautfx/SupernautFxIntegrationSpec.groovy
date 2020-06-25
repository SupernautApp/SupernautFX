/*
 * Copyright 2019-2020 M. Sean Gilligan.
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
package org.consensusj.supernautfx

import io.micronaut.context.BeanContext
import javafx.fxml.FXMLLoader
import org.consensusj.supernautfx.micronaut.SfxFxmlLoaderFactory
import org.consensusj.supernautfx.sample.hello.HelloForegroundApp

import spock.lang.Ignore
import spock.lang.Specification

/**
 *
 */
class SupernautFxIntegrationSpec extends Specification {
    def "Can create and find an FXMLLoader provider/factory"() {
        when:
        BeanContext ctx = BeanContext.build()
        def loaderFactory = new SfxFxmlLoaderFactory(ctx);
        ctx.registerSingleton(SfxFxmlLoaderFactory.class, loaderFactory);
        ctx.start();
        SfxFxmlLoaderFactory foundFactory = ctx.getBean(SfxFxmlLoaderFactory.class);
        FXMLLoader loader = (FXMLLoader) foundFactory.get();

        then:
        foundFactory != null
        foundFactory instanceof SfxFxmlLoaderFactory
        loader != null
        loader instanceof FXMLLoader
    }

    @Ignore
    def "Can create an FXMLLoader factory and inject into test class"() {
        when:
        BeanContext ctx = BeanContext.build()
        def loaderFactory = new SfxFxmlLoaderFactory(ctx)
        ctx.registerSingleton(SfxFxmlLoaderFactory.class, loaderFactory)
        HelloForegroundApp testBean = ctx.createBean(HelloForegroundApp.class)
        SfxFxmlLoaderFactory foundFactory = ctx.getBean(Provider.class)


        then:
        foundFactory != null
        foundFactory instanceof SfxFxmlLoaderFactory
    }

}
