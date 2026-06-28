package com.anvil.fitness;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;

@DisableCachingByDefault(because = "Reads environment variables at execution time")
public abstract class CheckFitnessTask extends DefaultTask {

    @Input
    public abstract Property<String> getEnvVar();

    @Input
    public abstract Property<String> getFailOnValue();

    @TaskAction
    public void check() {
        String envVar = getEnvVar().get();
        String failOnValue = getFailOnValue().get();
        String value = System.getenv(envVar);
        if (failOnValue.equals(value)) {
            throw new GradleException(
                "Fitness function failed: env var '" + envVar + "' equals '" + failOnValue + "'");
        }
        getLogger().lifecycle("Fitness function passed: '{}' is not '{}'", envVar, failOnValue);
    }
}
