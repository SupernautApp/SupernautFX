package org.consensusj.supernautfx

import io.micronaut.context.BeanContext
import javafx.fxml.FXMLLoader
import org.consensusj.supernautfx.micronaut.SfxFxmlLoaderFactory
import spock.lang.Ignore
import spock.lang.Specification

/**
 *
 */
class FxmlLoaderFactoryIntegrationSpecification extends Specification {
    def "Can create and find FXMLLoader factory"() {
        when:
        BeanContext ctx = BeanContext.build()
        def loaderFactory = new SfxFxmlLoaderFactory(ctx);
        ctx.registerSingleton(SfxFxmlLoaderFactory.class, loaderFactory);
        ctx.start();
        SfxFxmlLoaderFactory foundFactory = ctx.getBean(SfxFxmlLoaderFactory.class);
        FXMLLoader loader = foundFactory.get();

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
        //TestBean testBean = ctx.createBean(TestBean.class)
        SfxFxmlLoaderFactory foundFactory = ctx.getBean(Provider.class)


        then:
        foundFactory != null
        foundFactory instanceof SfxFxmlLoaderFactory
    }
}
