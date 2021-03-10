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
 * Hello World Sample app for SupernautFX
 *
 * This application uses jakarta.inject annotations for dependency injection and has no direct
 * dependencies on Micronaut (with the exception of HelloAppFactory.java).
 * However it's module descriptor does require the use of Micronaut since the annotation
 * processor will generate
 * classes that need it.
 */
package app.supernaut.fx.sample.hello;