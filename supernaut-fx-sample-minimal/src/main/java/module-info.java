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
/**
 * SupernautFX Hello Sample app.
 * <p>
 * This application module uses and requires Micronaut dependency injection, but module {@code app.supernaut.fx.micronaut} is
 * not needed at compile time and should <b>not</b> be listed with a {@code requires} keyword to avoid unnecessary
 * dependencies. However because we are using Micronaut annotations (e.g. {@link io.micronaut.context.annotation.Factory})
 * and classes generated by the Micronaut annotation processor, module {@link io.micronaut.inject} is
 * required. For JLink, Module {@code app.supernaut.fx.micronaut} must be configured with {@code --add-modules}
 * so that {@code jlink} will include it. See the {@code options} configuration in the {@code jlink} block in
 * this applications {@code build.gradle}.
 * <p>
 * In general, you should prefer the {@link jakarta.inject} annotations for portability reasons.
 *
 *
 * @uses app.supernaut.fx.FxLauncher You must provide the {@code app.supernaut.fx.micronaut} implementation at runtime.
 */
module app.supernaut.fx.sample.minimal {

    requires javafx.graphics;
    requires javafx.controls;

    requires app.supernaut.fx;
    requires static jakarta.inject;
    
    /* Needed for Micronaut-generated classes, see JavaDoc comment above */
    requires static io.micronaut.inject;

    requires org.slf4j;
    requires java.logging;

    opens app.supernaut.fx.sample.minimal to javafx.graphics, java.base;
    exports app.supernaut.fx.sample.minimal;

    uses app.supernaut.fx.FxLauncher;
}