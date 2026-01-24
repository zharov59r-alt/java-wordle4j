package ru.yandex.practicum;

import ru.yandex.practicum.exception.SystemException;
import ru.yandex.practicum.logger.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    public static final String DICTIONARY_FILE_NAME = "words_ru.txt";
    private Logger logger;

    public WordleDictionaryLoader(Logger logger) {
        this.logger = logger;
    }

    public WordleDictionary load() throws IOException, SystemException {

        WordleDictionary wordleDictionary = new WordleDictionary(logger);

        Path path = Paths.get(DICTIONARY_FILE_NAME);

        if (!Files.exists(path)) {
            throw new SystemException("Файл словаря отсутствует");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(DICTIONARY_FILE_NAME, StandardCharsets.UTF_8))) {
            String line;

			while ((line = reader.readLine()) != null) {
                if (line.length() == 5) {
                    wordleDictionary.add(line.toLowerCase().replace("ё", "е"));
                }

            }
        }

        if (wordleDictionary.isEmpty()) {
            throw new SystemException("Словарь пуст");
        }

        return wordleDictionary;
    }
}
