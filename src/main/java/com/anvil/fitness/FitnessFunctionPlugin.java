package com.anvil.fitness;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class FitnessFunctionPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        FitnessFunctionExtension ext = project.getExtensions().create("fitnessFunction", FitnessFunctionExtension.class);
        project.getTasks().register("checkFitness", CheckFitnessTask.class, task -> {
            task.setGroup("verification");
            task.setDescription("Fails the build if the configured env var matches the fail value.");
            task.getEnvVar().convention(project.provider(ext::getEnvVar));
            task.getFailOnValue().convention(project.provider(ext::getFailOnValue));
        });
    }
}
