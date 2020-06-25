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

import java.lang.reflect.InvocationTargetException;

/**
 * A simple {@link SfxLauncher} that uses {@link Class} objects to specify {@link BackgroundApp}
 * and {@link SfxForegroundApp}.
 */
public final class SimpleJfxLauncher extends SfxLauncher {
    public SimpleJfxLauncher(Class<? extends BackgroundApp> backgroundAppClass,
                             Class<? extends SfxForegroundApp> foregroundAppClass,
                             boolean backgroundStart) {
        super(() -> new LambdaAppFactory(
                        () -> newInstance(backgroundAppClass),
                        () -> newInstance(foregroundAppClass)
                ),
                backgroundStart);
    }

    /**
     * newInstance without checked exceptions.
     *
     * @param clazz A Class object that must have a no-args constructor.
     * @param <T> The type of the class
     * @return A new instanceof the class
     * @throws RuntimeException exceptions thrown by {@code newInstance()}.
     */
    private static <T> T newInstance(Class<T> clazz) {
        T foregroundApp;
        try {
            foregroundApp = clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return foregroundApp;
    }
}
