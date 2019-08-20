package org.consensusj.supernautfx

import io.micronaut.context.BeanContext
import javafx.fxml.FXMLLoader
import spock.lang.Ignore
import spock.lang.Specification

/**
 *
 */
class FxmlLoaderFactoryIntegrationTest extends Specification {
    def "Can create and find FXMLLoader factory"() {
        when:
        BeanContext ctx = BeanContext.build()
        def loaderFactory = new FxmlLoaderFactory(ctx);
        ctx.registerSingleton(FxmlLoaderFactory.class, loaderFactory);
        ctx.start();
        FxmlLoaderFactory foundFactory = ctx.getBean(FxmlLoaderFactory.class);
        FXMLLoader loader = foundFactory.get();

        then:
        foundFactory != null
        foundFactory instanceof FxmlLoaderFactory
        loader != null
        loader instanceof FXMLLoader
    }

    @Ignore
    def "Can create an FXMLLoader factory and inject into test class"() {
        when:
        BeanContext ctx = BeanContext.build()
        def loaderFactory = new FxmlLoaderFactory(ctx)
        ctx.registerSingleton(FxmlLoaderFactory.class, loaderFactory)
        //TestBean testBean = ctx.createBean(TestBean.class)
        FxmlLoaderFactory foundFactory = ctx.getBean(Provider.class)


        then:
        foundFactory != null
        foundFactory instanceof FxmlLoaderFactory
    }
}
