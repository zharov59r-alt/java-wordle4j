package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exception.SystemException;
import ru.yandex.practicum.logger.Logger;
import ru.yandex.practicum.logger.TestLogger;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordleTest {

    public static Logger logger;
    public static WordleDictionaryLoader wordleDictionaryLoader;
    public static WordleDictionary wordleDictionary;
    public static WordleGame wordleGame;

    @BeforeAll
    public static void beforeAll() throws IOException, SystemException {
        logger = new TestLogger();
        wordleDictionaryLoader = new WordleDictionaryLoader(logger);
        wordleDictionary = wordleDictionaryLoader.load();
    }

    @BeforeEach
    public void beforeEach() {
        wordleGame = new WordleGame(wordleDictionary, logger, 6);
    }

    @Test
    void checkAnswerMask() {
        wordleGame.setRigthAnswer("аверс");
        assertEquals("+^--^", wordleGame.analyzeWord("аезор"));
    }

    @Test
    void checkHint() {
        wordleGame.setRigthAnswer("бобер");
        wordleGame.setAnswer("аббат");
        wordleGame.doStep();
        String hint = wordleGame.getHint();

        assertEquals('б', hint.charAt(0));
        assertEquals('б', hint.charAt(2));
    }

}
