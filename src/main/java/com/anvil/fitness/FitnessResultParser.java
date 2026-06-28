package com.anvil.fitness;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FitnessResultParser {

    private static final Pattern MARKER = Pattern.compile("FITNESS_VALUE:\\s*([\\d.]+)");

    public double parse(String response) {
        Matcher matcher = MARKER.matcher(response);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        throw new IllegalStateException(
                "Could not find FITNESS_VALUE marker in response. Response was:\n" + response);
    }
}
