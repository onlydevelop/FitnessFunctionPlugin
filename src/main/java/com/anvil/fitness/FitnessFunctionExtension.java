package com.anvil.fitness;

public class FitnessFunctionExtension {
    private String agent;
    private String model;
    private String prompt;
    private String fitnessFunction;
    private double threshold;

    public String getAgent() { return agent; }
    public void setAgent(String agent) { this.agent = agent; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getFitnessFunction() { return fitnessFunction; }
    public void setFitnessFunction(String fitnessFunction) { this.fitnessFunction = fitnessFunction; }

    public double getThreshold() { return threshold; }
    public void setThreshold(double threshold) { this.threshold = threshold; }
}
