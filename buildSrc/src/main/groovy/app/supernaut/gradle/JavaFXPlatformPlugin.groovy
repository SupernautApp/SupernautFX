package app.supernaut.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Simple Plugin to initialize ext.jfxPlatform to the JavaFX platform for dependency declarations
 */
class JavaFXPlatformPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.ext.jfxPlatform = getJfxPlatform()
    }

    static String getJfxPlatform() {
        def osName = System.getProperty("os.name").toLowerCase()
        def platform
        switch (osName) {
            case ~/mac.*/:
                platform = "mac"
                break
            case ~/windows.*/:
                platform = "win"
                break
            case ~/linux.*/:
                platform = "linux"
                break
            default:
                platform = "unknown"
                break
        }
        return platform
    }
}
