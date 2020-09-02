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
package app.supernaut.test;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Simple class for recording timestamps for benchmarking
 */
public class TimingMeasurements {
    public final long startTime = System.currentTimeMillis();
    public final ConcurrentLinkedQueue<Measurement> measurements = new ConcurrentLinkedQueue<>();

    public void add(String desc) {
        measurements.add(new Measurement(desc));
    }

    public void dump() {
        measurements.forEach(this::printOne);
    }

    private void printOne(Measurement m) {
        System.out.println(m.timestamp + " " + m.description);
    }

    public class Measurement {
        public final long timestamp;
        public final String description;

        public Measurement(String desc) {
            timestamp = System.currentTimeMillis() - startTime;
            description = desc;
        }
    }
}
