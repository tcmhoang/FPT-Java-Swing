package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private final Path path;


    public FileManager(String dir) {
        path = Paths.get(dir);
        if (Files.notExists(path)) throw new InvalidPathException(dir, "Cannot found data file");


    }

    public BufferedReader getReader() {
        try {
            return Files.newBufferedReader(path);
        } catch (IOException e) {
//            System.err.println("This error should never happen");
        }
        return null;
    }

    public void write(String data) throws IOException {
        Files.write(path, data.getBytes(StandardCharsets.UTF_8));
    }
}
