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
package org.consensusj.supernautfx.sample.testapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import org.consensusj.supernaut.services.BrowserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.net.URI;

/**
 * TestApp Main Window Controller
 * Uses JSR 330 dependency injection annotations to tell SupernautFX/Micronaut
 * what to inject in its constructor. @FXML annotations tell the FXMLLoader where
 * to inject JFX objects from the FXML file.
 */
@Singleton
public class MainWindowView {
    private static final Logger log = LoggerFactory.getLogger(MainWindowView.class);
    private static final URI githubRepoUri = URI.create("https://github.com/ConsensusJ/SupernautFX");

    @FXML
    private Hyperlink githubLink;

    @FXML
    private Button btn;

    public MainWindowView(BrowserService browserService) {
    }

    /**
     * Called by FXMLLoader to initialize the controller.
     */
    @FXML
    public void initialize() {
    }
    
    @FXML
    public void buttonAction(ActionEvent event) {
        log.info("buttonAction: quit, calling Platform.exit()");
        Platform.exit();
    }

    @FXML
    public void linkAction(ActionEvent actionEvent) {
        //browserService.showDocument(githubRepoUri);
    }
}
