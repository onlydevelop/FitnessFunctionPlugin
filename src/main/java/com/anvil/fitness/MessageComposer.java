package com.anvil.fitness;

public class MessageComposer {

    public String compose(String prompt, String fitnessFunction, double threshold, String sourceContent) {
        StringBuilder sb = new StringBuilder();
        sb.append(prompt).append("\n\n");
        sb.append("Fitness Function: ").append(fitnessFunction).append("\n");
        sb.append("Threshold: ").append(formatThreshold(threshold)).append("\n");
        if (!sourceContent.isEmpty()) {
            sb.append("\n").append(sourceContent);
        }
        sb.append("\nAt the end of your response, output exactly this line: FITNESS_VALUE: <computed_total>");
        return sb.toString();
    }

    private String formatThreshold(double threshold) {
        if (threshold == Math.floor(threshold) && !Double.isInfinite(threshold)) {
            return String.valueOf((long) threshold);
        }
        return String.valueOf(threshold);
    }
}
