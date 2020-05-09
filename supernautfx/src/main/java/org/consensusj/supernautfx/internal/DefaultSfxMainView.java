package org.consensusj.supernautfx.internal;

import javafx.stage.Stage;
import org.consensusj.supernautfx.SfxForegroundApp;

import java.util.Optional;

/**
 * Default, Internal implementation of {@link SfxForegroundApp.SfxMainView}.
 * Simply wraps a primary {@link Stage}.
 * TODO: For extra credit create a macOS-friendly implementation of SfxMainView with an invisible primaryStage
 */
public final class DefaultSfxMainView implements SfxForegroundApp.SfxMainView {
    private final Stage primaryStage;

    private DefaultSfxMainView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public static SfxForegroundApp.SfxMainView of(Stage primaryStage) {
        return new DefaultSfxMainView(primaryStage);
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
