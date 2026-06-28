package com.anvil.fitness;

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

        SourceCollector collector = new SourceCollector();
        String sourceContent = collector.collect(getSourceFiles());

        String message = sourceContent.isEmpty()
                ? prompt
                : prompt + "\n\n" + sourceContent;

        getLogger().lifecycle("Running fitness check with model: {}", model);
        getLogger().lifecycle("Prompt: {}", prompt);

        AnthropicClient client = new AnthropicClient();
        String result = client.sendMessage(apiKey, model, message);

        getLogger().lifecycle("=== Fitness Function Result ===\n{}", result);
    }
}
