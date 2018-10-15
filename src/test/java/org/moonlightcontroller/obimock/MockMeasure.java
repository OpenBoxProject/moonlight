package org.moonlightcontroller.obimock;

import java.util.logging.Logger;

public class MockMeasure {

    private volatile double measure = Math.random() * 100;
    private final static Logger log = Logger.getLogger(ObiMockApi.class.getName());
    private final String label;

    public MockMeasure(String label) {
        this.label = label;
    }

    public synchronized double next() {
        double oldVal = measure;
        log.fine(label + "=" + measure);
        measure = bound(measure  + (Math.random()-0.5) * 2);
        log.fine(label + "=" + measure + " old="+oldVal);
        return measure;
    }

    private double bound(double value) {
        return Math.min(Math.max(value, 0), 100);
    }

}
