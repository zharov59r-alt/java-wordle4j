package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    private String rigthAnswer;

    private String answer;

    private int steps;

    private WordleDictionary dictionary;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
    }

    List<String> answers = new ArrayList<>();

    public void run(PrintWriter logFileWriter) throws IOException {

        rigthAnswer = dictionary.getRandomWord();
        System.out.println(rigthAnswer);
        logFileWriter.write("rigth answer: " + rigthAnswer + "\n");

        Scanner scanner = new Scanner(System.in);

        while (steps != 6) {
            String input = scanner.nextLine();
            logFileWriter.write("input: " + input + "\n");

            answer = input.toLowerCase().replace("ё", "е");

            if (answer.isEmpty()) {
                System.out.println("Подсказка");
            } else if (answer.length() != 5) {
                System.out.println("Слово должно состоять из пяти букв");
            } else if (dictionary.checkWord(answer)) {
                System.out.println("Слово отсутствует в словаре");
            } else if (answer.equals(rigthAnswer))
                System.out.println("Вы выйграли");
            else {
                steps++;
                answers.add(answer);

                System.out.println(checkAnswer());
            }

        }

    }

    public String checkAnswer() {

        System.out.println(this.answer);

        StringBuilder result = new StringBuilder(5);
        boolean exists;

        for (int i = 0; i < 5; i++) {
            exists = false;
            for (int j = 0; j < 5; j++) {
                if (answer.charAt(i) == rigthAnswer.charAt(j)) {
                    result.setCharAt(i, (i == j) ? '+' : '^');
                    exists = true;
                }
            }

            if (!exists) {
                result.setCharAt(i, '-');
            }

        }

        return result.toString();
    }

}
