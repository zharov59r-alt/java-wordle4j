package ru.yandex.practicum;

import ru.yandex.practicum.logger.Logger;

import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private Logger logger;
    private String rigthAnswer;
    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private List<String> answers = new ArrayList<>();
    private List<String> wordsForHints = new ArrayList<>();
    private Map<Character, Character> letters = new HashMap<>();

    public WordleGame(WordleDictionary dictionary, Logger logger, int steps) {
        this.dictionary = dictionary;
        this.logger = logger;
        this.rigthAnswer = dictionary.getRandomWord();
        this.steps = steps;
    }

    public String getRigthAnswer() {
        return rigthAnswer;
    }

    public void setRigthAnswer(String rigthAnswer) {
        this.rigthAnswer = rigthAnswer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean checkWordExistsInDictionary(String word) {
        return dictionary.wordExistsInDict(word);
    }

    public boolean checkWordHasAlreadyBeen(String word) {
        return answers.contains(word);
    }

    public boolean answerIsRight() {
        return answer.equals(rigthAnswer);
    }

    public String doStep() {
        steps--;
        String answerMask = analyzeWord(answer);
        answers.add(answer);
        refreshWordsForHints();
        return answerMask;
    }

    public boolean existsStep() {
        return this.steps != 0;
    }

    public String analyzeWord(String answer) {

        StringBuilder result = new StringBuilder("-----");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (answer.charAt(i) == rigthAnswer.charAt(j)) {
                    char compare = ((i == j) ? '+' : '^');
                    if (result.charAt(i) != '+') {
                        result.setCharAt(i, compare);
                    }
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if (!letters.containsKey(answer.charAt(i)) ||
                    letters.get(answer.charAt(i)) == '^' && result.charAt(i) == '+') {
                letters.put(answer.charAt(i), result.charAt(i));
            }
        }

        return result.toString();

    }

    public String getHint() {
        if (wordsForHints.isEmpty()) return dictionary.get(0);
        return wordsForHints.get(0);
    }

    public void refreshWordsForHints() {

        List<String> temp = new ArrayList<>();

        for (String word : (wordsForHints.isEmpty()) ? dictionary.getAllWords() : wordsForHints) {

            if (!answers.contains(word)) {

                boolean checkWord = true;

                for (var entry : letters.entrySet()) {

                    if (entry.getValue() == '-' && word.indexOf(entry.getKey()) != -1 ||
                        entry.getValue() == '^' && word.indexOf(entry.getKey()) == -1) {
                        checkWord  = false;
                        break;
                    }

                    if (entry.getValue() == '+') {
                        for (int i = 0; i < 5; i++) {
                            if (rigthAnswer.charAt(i) == entry.getKey() && rigthAnswer.charAt(i) != word.charAt(i)) {
                                checkWord = false;
                                break;
                            }
                        }
                    }

                }

                if (checkWord) {
                    temp.add(word);
                }
            }

        }

        wordsForHints = temp;

    }

}
