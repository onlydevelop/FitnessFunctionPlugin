package com.anvil.fitness;

import org.gradle.api.GradleException;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;

@DisableCachingByDefault(because = "Reads environment variables and calls external API at execution time")
public abstract class CheckFitnessTask extends DefaultTask {

    @Input
    public abstract Property<String> getAgent();

    @Input
    public abstract Property<String> getModel();

    @Input
    public abstract Property<String> getPrompt();

    @Input
    public abstract Property<String> getFitnessFunction();

    @Input
    public abstract Property<Double> getThreshold();

    @InputFiles
    @Optional
    public abstract ConfigurableFileCollection getSourceFiles();

    @TaskAction
    public void check() {
        String apiKey = System.getenv("agentKey");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("agentKey environment variable is not set");
        }

        String model = getModel().get();
        String prompt = getPrompt().get();
        String fitnessFunction = getFitnessFunction().get();
        double threshold = getThreshold().get();

        SourceCollector collector = new SourceCollector();
        String sourceContent = collector.collect(getSourceFiles());

        String message = new MessageComposer().compose(prompt, fitnessFunction, threshold, sourceContent);

        getLogger().lifecycle("Running fitness check with model: {}", model);
        getLogger().lifecycle("Prompt: {}", prompt);

        AnthropicClient client = new AnthropicClient();
        String result = client.sendMessage(apiKey, model, message);

        getLogger().lifecycle("=== Fitness Function Result ===\n{}", result);

        double value = new FitnessResultParser().parse(result);
        getLogger().lifecycle("Fitness value: {} (threshold: {})", value, threshold);

        if (value > threshold) {
            throw new GradleException(
                    "Fitness function value " + value + " exceeds threshold " + threshold + ". Build failed.");
        }
    }
}
