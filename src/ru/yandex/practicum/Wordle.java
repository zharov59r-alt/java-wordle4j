package ru.yandex.practicum;

import ru.yandex.practicum.exception.GameException;
import ru.yandex.practicum.exception.SystemException;
import ru.yandex.practicum.logger.GameLogger;
import ru.yandex.practicum.logger.Logger;

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

    public static void main(String[] args) throws Exception {

        try (Logger logger = new GameLogger()) {

            try {
                WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(logger);
                WordleDictionary wordleDictionary = wordleDictionaryLoader.load();
                WordleGame wordleGame = new WordleGame(wordleDictionary, logger, 6);

                Scanner scanner = new Scanner(System.in);

                while (true) {

                    try {
                        String input = scanner.nextLine().toLowerCase().replace("ё", "е");

                        if (input.isEmpty()) {
                            input = wordleGame.getHint();
                            System.out.println(input);
                        } else {
                            if (input.length() != 5) throw new GameException("Слово должно состоять из пяти букв");
                            if (!input.matches("^[а-яА-ЯёЁ]+$"))
                                throw new GameException("Слово должно состоять из букв русского языка");
                            if (!wordleGame.checkWordExistsInDictionary(input))
                                throw new GameException("Введенное слово отсутствует в словаре");
                            if (wordleGame.checkWordHasAlreadyBeen(input))
                                throw new GameException("Введенное слово уже было");
                        }

                        wordleGame.setAnswer(input);

                        if (wordleGame.answerIsRight()) {
                            System.out.println("Вы выйграли!");
                            break;
                        }

                        System.out.println(wordleGame.doStep());

                        if (!wordleGame.existsStep()) {
                            System.out.println("Вы проиграли! Правильный ответ: " + wordleGame.getRigthAnswer());
                            break;
                        }

                    } catch (GameException e) {
                        System.out.println(e.getMessage());
                    }

                }

            } catch (SystemException e) {
                logger.log(e.getMessage());
            } catch (Exception e) {
                for (StackTraceElement ste : e.getStackTrace()) {
                    logger.log(ste.toString());
                }
            }

        }

    }

}
