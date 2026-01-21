package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    public WordleDictionary Loader(String fileName) {

        WordleDictionary wordleDictionary = new WordleDictionary();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;

			while ((line = reader.readLine()) != null) {
                if (line.length() == 5) {
                    wordleDictionary.add(line.toLowerCase().replace("ё", "е"));
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return wordleDictionary;
    }
}
