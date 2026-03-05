package ru.yandex.practicum;

import ru.yandex.practicum.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words = new ArrayList<>();
    private Logger logger;

    public WordleDictionary(Logger logger) {
        this.logger = logger;
    }

    public void add(String word) {
        words.add(word);
    }

    public String get(int index) {
        return words.get(index);
    }

    public List<String> getAllWords() {
        return words;
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }

    public String getRandomWord() {
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }

    public boolean wordExistsInDict(String word) {
        return words.contains(word);
    }


}
