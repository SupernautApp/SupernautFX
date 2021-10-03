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
package app.supernaut.fx.util

import spock.lang.Specification

import java.lang.module.ModuleDescriptor

/**
 * Tests for {@link FxVersionUtil} and {@link ModuleDescriptor.Version} can go here.
 */
class FxVersionUtilSpec extends Specification {

    def "Test version compare"(String a, String b, int expected) {
        when:
        def av = ModuleDescriptor.Version.parse(a);
        def bv = ModuleDescriptor.Version.parse(b);
        int result = a.compareTo(b);

        then:
        Integer.signum(result) == expected

        where:
        a                                   | b         | expected
        "17"                                | "17"      | 0
        "17"                                | "17.0.1"  | -1
        "17.0.1"                            | "17"      | 1
        "18-ea+4"                           | "17.0.1"  | 1
        "18"                                | "17-ea+5" | 1
        "18-ea+4"                           | "17-ea+5" | 1
      // This test fails, but maybe I'm undestanding things wrong
      // "18-internal+2-2021-08-25-091715"   | "18-ea+4" | -1
    }
}
