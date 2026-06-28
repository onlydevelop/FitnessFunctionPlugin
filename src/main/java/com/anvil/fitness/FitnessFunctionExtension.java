package com.anvil.fitness;

public class FitnessFunctionExtension {
    private String envVar = "FITNESS_FUNCTION_FAIL";
    private String failOnValue = "true";

    public String getEnvVar() { return envVar; }
    public void setEnvVar(String envVar) { this.envVar = envVar; }

    public String getFailOnValue() { return failOnValue; }
    public void setFailOnValue(String failOnValue) { this.failOnValue = failOnValue; }
}
