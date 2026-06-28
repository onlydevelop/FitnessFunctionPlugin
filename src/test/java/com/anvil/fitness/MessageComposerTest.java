package com.anvil.fitness;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageComposerTest {

    private final MessageComposer composer = new MessageComposer();

    @Test
    void includesPromptInMessage() {
        String result = composer.compose("analyze smells", "h*3 + l*1", 20.0, "");
        assertTrue(result.contains("analyze smells"));
    }

    @Test
    void includesFitnessFunctionFormula() {
        String result = composer.compose("analyze", "high x 3.5 + low x 1.8", 20.0, "");
        assertTrue(result.contains("high x 3.5 + low x 1.8"));
    }

    @Test
    void includesThreshold() {
        String result = composer.compose("analyze", "h*3", 15.0, "");
        assertTrue(result.contains("15"));
    }

    @Test
    void formatsWholeThresholdWithoutDecimal() {
        String result = composer.compose("analyze", "h*3", 20.0, "");
        assertTrue(result.contains("20"));
        assertFalse(result.contains("20.0"));
    }

    @Test
    void includesSourceContentWhenPresent() {
        String result = composer.compose("analyze", "h*3", 20.0, "class Foo {}");
        assertTrue(result.contains("class Foo {}"));
    }

    @Test
    void omitsSourceSectionWhenEmpty() {
        String result = composer.compose("analyze", "h*3", 20.0, "");
        assertFalse(result.isBlank());
        assertFalse(result.contains("null"));
    }
}
