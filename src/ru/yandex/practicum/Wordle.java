package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static final String logFileName = "log.txt";
    public static final String dictionaryFileName = "words_ru.txt";

    public static void main(String[] args) {

        Path logFile = Paths.get(logFileName);

        if (!Files.exists(logFile)) {
            try {
                Files.createFile(logFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (PrintWriter logFileWriter = new PrintWriter( logFileName )) {

            try {
                WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader();
                WordleDictionary wordleDictionary = wordleDictionaryLoader.Loader(dictionaryFileName);
                WordleGame wordleGame = new WordleGame(wordleDictionary);
                wordleGame.run(logFileWriter);

            } catch (Exception e) {
                logFileWriter.write(e.getMessage());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
