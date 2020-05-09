package org.consensusj.supernaut.test;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
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
