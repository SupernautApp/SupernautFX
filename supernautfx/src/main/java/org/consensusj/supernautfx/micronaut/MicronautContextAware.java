package org.consensusj.supernautfx.micronaut;

import io.micronaut.context.BeanContext;
import org.consensusj.supernaut.BackgroundApp;

/**
 *  TODO: Use this somewhere to inject the context
 */
public interface MicronautContextAware extends BackgroundApp {
    void setBeanFactory(BeanContext context);
}
