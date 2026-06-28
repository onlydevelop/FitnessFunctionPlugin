package com.anvil.fitness;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class FitnessFunctionPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        FitnessFunctionExtension ext = project.getExtensions().create("fitnessFunction", FitnessFunctionExtension.class);
        project.getTasks().register("checkFitness", CheckFitnessTask.class, task -> {
            task.setGroup("verification");
            task.setDescription("Prints the configured agent and model.");
            task.getAgent().set(project.provider(ext::getAgent));
            task.getModel().set(project.provider(ext::getModel));
            task.getPrompt().set(project.provider(ext::getPrompt));
        });
    }
}
