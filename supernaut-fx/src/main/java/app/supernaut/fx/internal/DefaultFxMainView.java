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
package app.supernaut.fx.internal;

import javafx.stage.Stage;
import app.supernaut.fx.FxForegroundApp;

import java.util.Optional;

/**
 * Default, Internal implementation of {@link FxForegroundApp.FxMainView}.
 * Simply wraps a primary {@link Stage}.
 * TODO: For extra credit create a macOS-friendly implementation of FxMainView with an invisible primaryStage
 */
public final class DefaultFxMainView implements FxForegroundApp.FxMainView {
    private final Stage primaryStage;

    private DefaultFxMainView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public static FxForegroundApp.FxMainView of(Stage primaryStage) {
        return new DefaultFxMainView(primaryStage);
    }

    @Override
    public void show() {
        primaryStage.show();
    }

    @Override
    public Optional<Stage> optionalStage() {
        return Optional.of(primaryStage);
    }
}
