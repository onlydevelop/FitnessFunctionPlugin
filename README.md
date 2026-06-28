# Fitness Function Plugin

A Gradle plugin that prints the configured agent and model when `checkFitness` is run.

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

Both `agent` and `model` are required.

```groovy
fitnessFunction {
    agent = 'claude'
    model = 'claude-sonnet-4-6'
}
```

### 4. Set the `AGENT_KEY` environment variable

`AGENT_KEY` is read at execution time and printed by the task.

```bash
export AGENT_KEY=your-api-key
```

### 5. Run the check

```bash
./gradlew checkFitness
```

Wire it into your build by making `build` depend on it:

```groovy
tasks.named('build') {
    dependsOn 'checkFitness'
}
```
