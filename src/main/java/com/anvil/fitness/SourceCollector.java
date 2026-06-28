package com.anvil.fitness;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;

public class SourceCollector {

    public String collect(Iterable<File> sourceFiles) {
        StringBuilder sb = new StringBuilder();
        for (File file : sourceFiles) {
            sb.append("// File: ").append(file.getName()).append("\n");
            try {
                sb.append(Files.readString(file.toPath()));
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to read " + file, e);
            }
            sb.append("\n\n");
        }
        return sb.toString();
    }
}
