package com.anvil.fitness;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;

@DisableCachingByDefault(because = "Reads environment variables at execution time")
public abstract class CheckFitnessTask extends DefaultTask {

    @Input
    public abstract Property<String> getAgent();

    @Input
    public abstract Property<String> getModel();

    @TaskAction
    public void check() {
        getLogger().lifecycle("agent: {}", getAgent().get());
        getLogger().lifecycle("model: {}", getModel().get());
        getLogger().lifecycle("AGENT_KEY: {}", System.getenv("AGENT_KEY"));
    }
}
