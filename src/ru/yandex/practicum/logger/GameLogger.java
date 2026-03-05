package ru.yandex.practicum.logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameLogger implements Logger {

    private static final String LOG_FILE_NAME = "log.txt";

    private final PrintWriter writer;

    public GameLogger() throws IOException {
        Path logFile = Paths.get(LOG_FILE_NAME);
        if (!Files.exists(logFile)) Files.createFile(logFile);
        writer = new PrintWriter(LOG_FILE_NAME);
    }

    public void log(String message) {
        writer.println(message);
        writer.flush();
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }

}
