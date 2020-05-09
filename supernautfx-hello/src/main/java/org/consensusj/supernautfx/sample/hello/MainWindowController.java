package org.consensusj.supernautfx.sample.hello;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import org.consensusj.supernaut.services.BrowserService;
import org.consensusj.supernautfx.sample.hello.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.net.URI;

/**
 * Main Window Controller
 * Uses JSR 330 dependency injection annotations to tell SupernautFX/Micronaut
 * what to inject in its constructor. @FXML annotations tell the FXMLLoader where
 * to inject objects from the FXML file.
 */
@Singleton
public class MainWindowController {
    private static final Logger log = LoggerFactory.getLogger(MainWindowController.class);
    private static final URI githubRepoUri = URI.create("https://github.com/ConsensusJ/SupernautFX");
    private final BrowserService browserService;
    private final GreetingService greetingService;

    @FXML
    private Hyperlink githubLink;

    @FXML
    private Label message;

    @FXML
    private Button btn;

    public MainWindowController(BrowserService browserService, GreetingService greetingService) {
        this.greetingService = greetingService;
        this.browserService = browserService;
    }

    /**
     * Called by FXMLLoader to initialize the controller.
     */
    @FXML
    public void initialize() {
        var planet = greetingService.getPlanetName();
        btn.setText("Say Hello to " + planet);

    }
    
    @FXML
    public void buttonAction(ActionEvent event) {
        var greeting = greetingService.greeting();
        log.info("buttonAction: greeting: {}", greeting);
        message.setText(greetingService.greeting());
    }

    @FXML
    public void linkAction(ActionEvent actionEvent) {
        browserService.showDocument(githubRepoUri);
    }
}
