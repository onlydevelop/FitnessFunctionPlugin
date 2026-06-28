package com.anvil.fitness;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SourceCollectorTest {

    @TempDir
    Path tempDir;

    @Test
    void returnsEmptyStringWhenNoFiles() {
        SourceCollector collector = new SourceCollector();
        assertEquals("", collector.collect(List.of()));
    }

    @Test
    void collectsContentFromJavaFile() throws IOException {
        File javaFile = tempDir.resolve("Foo.java").toFile();
        Files.writeString(javaFile.toPath(), "class Foo {}");

        SourceCollector collector = new SourceCollector();
        String result = collector.collect(List.of(javaFile));

        assertTrue(result.contains("class Foo {}"));
    }

    @Test
    void includesFilenameInOutput() throws IOException {
        File javaFile = tempDir.resolve("Bar.java").toFile();
        Files.writeString(javaFile.toPath(), "class Bar {}");

        SourceCollector collector = new SourceCollector();
        String result = collector.collect(List.of(javaFile));

        assertTrue(result.contains("Bar.java"));
    }

    @Test
    void concatenatesMultipleFiles() throws IOException {
        File foo = tempDir.resolve("Foo.java").toFile();
        File bar = tempDir.resolve("Bar.java").toFile();
        Files.writeString(foo.toPath(), "class Foo {}");
        Files.writeString(bar.toPath(), "class Bar {}");

        SourceCollector collector = new SourceCollector();
        String result = collector.collect(List.of(foo, bar));

        assertTrue(result.contains("class Foo {}"));
        assertTrue(result.contains("class Bar {}"));
    }
}
