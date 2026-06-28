package com.anvil.fitness;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FitnessResultParserTest {

    private final FitnessResultParser parser = new FitnessResultParser();

    @Test
    void extractsValueFromMarker() {
        double value = parser.parse("Some analysis.\nFITNESS_VALUE: 42.5");
        assertEquals(42.5, value);
    }

    @Test
    void extractsWholeNumber() {
        double value = parser.parse("FITNESS_VALUE: 20");
        assertEquals(20.0, value);
    }

    @Test
    void extractsValueWithSpaceVariants() {
        double value = parser.parse("FITNESS_VALUE:  17.3\n");
        assertEquals(17.3, value, 0.001);
    }

    @Test
    void extractsValueWhenEmbeddedInLongerResponse() {
        String response = "High: 2, Medium: 3, Low: 1\nTotal: 17.3\nFITNESS_VALUE: 17.3\nFail.";
        double value = parser.parse(response);
        assertEquals(17.3, value, 0.001);
    }

    @Test
    void throwsWhenMarkerAbsent() {
        assertThrows(IllegalStateException.class, () -> parser.parse("No marker here."));
    }
}
