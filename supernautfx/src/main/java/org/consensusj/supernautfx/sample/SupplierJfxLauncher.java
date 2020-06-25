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
package org.consensusj.supernautfx.sample;

import org.consensusj.supernaut.BackgroundApp;
import org.consensusj.supernautfx.SfxLauncher;
import org.consensusj.supernautfx.SfxForegroundApp;

import java.util.function.Supplier;

/**
 * Demonstration Launcher that uses LambdaAppFactory.
 */
public class SupplierJfxLauncher extends SfxLauncher {

    public SupplierJfxLauncher(Supplier<BackgroundApp> backgroundAppSupplier,
                               Supplier<SfxForegroundApp> foregroundAppSupplier,
                               boolean backgroundStart) {
        super(() -> new LambdaAppFactory(backgroundAppSupplier, foregroundAppSupplier), backgroundStart);
    }
}
