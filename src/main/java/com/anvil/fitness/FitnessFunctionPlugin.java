package com.anvil.fitness;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;

public class FitnessFunctionPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        FitnessFunctionExtension ext = project.getExtensions().create("fitnessFunction", FitnessFunctionExtension.class);
        project.getTasks().register("checkFitness", CheckFitnessTask.class, task -> {
            task.setGroup("verification");
            task.setDescription("Runs the configured prompt against project source code using the Claude API.");
            task.getAgent().set(project.provider(ext::getAgent));
            task.getModel().set(project.provider(ext::getModel));
            task.getPrompt().set(project.provider(ext::getPrompt));
        });

        project.getPlugins().withId("java", unused -> {
            project.getTasks().named("checkFitness", CheckFitnessTask.class, task -> {
                JavaPluginExtension javaExt = project.getExtensions().getByType(JavaPluginExtension.class);
                SourceSet mainSourceSet = javaExt.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);
                task.getSourceFiles().from(mainSourceSet.getAllJava());
            });
        });
    }
}
