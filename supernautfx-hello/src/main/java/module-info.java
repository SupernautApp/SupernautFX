/**
 * Module descriptor for SupernautFX Hello Sample app
 */
module org.consensusj.supernautfx.sample.hello {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.consensusj.supernautfx;
    requires javax.inject;
    /*
     * Needed for the @Factory annotation and for the Bean Definition classes generated
     * by the Micronaut annotation processor. Most classes in this module only use annotations
     * from javax.inject and do not import any Micronaut classes. In general controllers and services
     * in a SupernautFX application should avoid dependencies on Micronaut and use javax.inject
     * annotations.
     */
    requires io.micronaut.inject;

    requires org.slf4j;

    opens org.consensusj.supernautfx.sample.hello to javafx.graphics, javafx.fxml, java.base;
    exports org.consensusj.supernautfx.sample.hello;
    exports org.consensusj.supernautfx.sample.hello.service;
}