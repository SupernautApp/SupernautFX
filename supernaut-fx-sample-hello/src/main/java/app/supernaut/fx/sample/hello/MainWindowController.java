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
package app.supernaut.fx.sample.hello;

import app.supernaut.fx.sample.hello.service.GreetingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import app.supernaut.services.BrowserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;
import java.net.URI;

/**
 * Main Window Controller
 * Uses JSR 330 dependency injection annotations to tell Supernaut.FX/Micronaut
 * what to inject in its constructor. @FXML annotations tell the FXMLLoader where
 * to inject objects from the FXML file.
 */
@Singleton
public class MainWindowController {
    private static final Logger log = LoggerFactory.getLogger(MainWindowController.class);
    private static final URI githubRepoUri = URI.create("https://github.com/SupernautApp/SupernautFX");
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
