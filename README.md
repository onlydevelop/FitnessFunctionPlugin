# Fitness Function Plugin

A Gradle plugin that fails a build when a configured environment variable matches a specified value. Useful as a fitness function to enforce build constraints in CI pipelines.

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

### 3. Configure the extension (optional)

The defaults are `envVar = "FITNESS_FUNCTION_FAIL"` and `failOnValue = "true"`.

```groovy
fitnessFunction {
    envVar      = 'MY_GATE_VAR'   // env var to check
    failOnValue = 'fail'          // build fails when the var equals this value
}
```

### 4. Run the check

```bash
./gradlew checkFitness
```

The `checkFitness` task is in the `verification` group. Wire it into your CI by making other tasks depend on it:

```groovy
tasks.named('build') {
    dependsOn 'checkFitness'
}
```

## Behaviour

| Env var value       | Result        |
|---------------------|---------------|
| equals `failOnValue` | Build **fails** |
| anything else (including unset) | Build **passes** |

### Example

```bash
# passes
./gradlew checkFitness

# fails
FITNESS_FUNCTION_FAIL=true ./gradlew checkFitness
```
