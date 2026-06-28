# Fitness Function Plugin

A Gradle plugin that runs a configured prompt against your project's Java source code using the Claude API, then prints the result.

## Build

Requires Java 21+ and Gradle 8+.

```bash
./gradlew build
```

To publish to your local Maven repository (for use in other local projects):

```bash
./gradlew publishToMavenLocal
```

## Use in another Gradle project

### 1. Add the plugin to `settings.gradle`

```groovy
pluginManagement {
    repositories {
        mavenLocal()         // pick up the locally published plugin
        gradlePluginPortal()
    }
}
```

### 2. Apply the plugin in `build.gradle`

```groovy
plugins {
    id 'com.anvil.fitness-function' version '1.0.0'
}
```

### 3. Configure the extension

`agent`, `model`, and `prompt` are all required.

```groovy
fitnessFunction {
    agent  = 'claude'
    model  = 'claude-sonnet-4-6'
    prompt = 'Analyze the refactoring fitness function'
}
```

### 4. Set the `agentKey` environment variable

`agentKey` must be your Anthropic API key. It is read at execution time.

```bash
export agentKey=your-anthropic-api-key
```

### 5. Run the check

```bash
./gradlew checkFitness
```

The task will:
1. Collect all `.java` files from `src/main/java` (when the `java` plugin is applied)
2. Send the configured prompt + source code to the Claude API
3. Print the response under `=== Fitness Function Result ===`

Wire it into your build by making `build` depend on it:

```groovy
tasks.named('build') {
    dependsOn 'checkFitness'
}
```
