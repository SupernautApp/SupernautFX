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
package app.supernaut.fx.util;

import java.lang.module.ModuleDescriptor;
import java.util.Locale;

/**
 * Various utilities methods for testing the current FX version and environment
 */
public class FxVersionUtil {
    /** Predicted fix version for macOS Browser Open on GraalVM native-image */
    static final ModuleDescriptor.Version MACOS_NATIVE_SHOW_DOC_VERSION = ModuleDescriptor.Version.parse("18-ea+4");
    /**
     * This doesn't work. You can't always trust Stack Overflow, I guess.
     * @see <a href="https://stackoverflow.com/questions/50264604/how-can-you-tell-if-your-java-program-is-running-in-a-graalvm-aot-context">Stack Overflow</a>
     */
    static final boolean IS_AOT = Boolean.getBoolean("com.oracle.graalvm.isaot");


    /**
     * @return The JavaFX Runtime Version in {@link ModuleDescriptor.Version} format
     */
    public static ModuleDescriptor.Version getJavaFXVersion() {
        return ModuleDescriptor.Version.parse(System.getProperty("javafx.runtime.version"));
    }
    
    /**
     * Is {@link javafx.application.HostServices#showDocument(String)} available?
     * <p>
     * Hyperlinks don't work on macOS in Graal native-image yet, but they should be available in the next EA release of OpenJFX 18.
     * <p>
     * For Browser open to be available one of the following must be true:
     * <ul>
     *     <li>OS is not macOS</li>
     *     <li>Not running in GraalVM native image</li>
     *     <li>JavaFX is later than {@link FxVersionUtil#MACOS_NATIVE_SHOW_DOC_VERSION}</li>
     * </ul>
     * NOTE: Currently we can only reliably check if we're macOS or not, so that's all we are checking.
     * @see <a href="https://github.com/SupernautApp/SupernautFX/issues/25">Supernaut.FX Issue #25</a>
     * @return true if available, false if not
     */
    public static boolean isShowDocumentAvailable() {
        return !isMacOS() /* || !IS_AOT || hasMacShowDocumentFix() */;
    }

    /**
     * @return true if this JavaFX version has the fix for macOS native-image showDocument
     */
    public static boolean hasMacShowDocumentFix() {
        boolean hasFix = getJavaFXVersion().compareTo(MACOS_NATIVE_SHOW_DOC_VERSION) >= 0;
        return hasFix;
    }

    /**
     * @return true if we're running on macOS
     */
    public static boolean isMacOS() {
        boolean isMacOS = System.getProperty("os.name", "").toLowerCase(Locale.US).contains("mac");
        return isMacOS;
    }
}
