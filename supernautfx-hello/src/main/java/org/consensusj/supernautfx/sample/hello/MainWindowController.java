package org.consensusj.supernautfx.sample.hello;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.consensusj.supernautfx.sample.hello.service.GreetingService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Main Window Controller
 * Uses JSR 330 dependency injection annotations to tell SupernautFX/Micronaut
 * what to inject in its constructor. @FXML annotations tell the FXMLLoader where
 * to inject objects from the FXML file.
 */
@Singleton
public class MainWindowController {
    private final GreetingService greetingService;

    @FXML
    private Button btn;

    public MainWindowController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    /**
     * Called by FXMLLoader to initialize the controller.
     */
    @FXML
    public void initialize() {
        String greeting = greetingService.greeting();
        btn.setText("Say '" + greeting + "'");
    }

    @FXML
    public void buttonAction(ActionEvent event) {
        System.out.println(greetingService.greeting());
    }
}
