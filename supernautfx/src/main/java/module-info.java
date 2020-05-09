/**
 *
 */
module org.consensusj.supernautfx {
    requires javafx.graphics;
    requires javafx.fxml;
    requires io.micronaut.inject;
    requires javax.inject;

    requires org.slf4j;

    /* TODO: Move java.logging, test support to another module */
    requires java.logging;
    exports org.consensusj.supernaut.logging;
    exports org.consensusj.supernaut.test;

    /* TODO: supernaut subpackage should DEFINITELY BE a separate module */
    exports org.consensusj.supernaut;
    exports org.consensusj.supernaut.services;

    exports org.consensusj.supernautfx;
    exports org.consensusj.supernautfx.internal to javafx.graphics;

    /* TODO: Micronaut subpackage should maybe be internal or in a separate module? */
    exports org.consensusj.supernautfx.micronaut;
    /* TODO: Fix this */
    /* We have to open this to so Micronaut (possibly in the merged module) can @Inject private fields in it */
    opens org.consensusj.supernautfx.micronaut;
    exports org.consensusj.supernautfx.test;
}