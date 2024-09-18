package org.example.gamecenter.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HigherLowerServiceTest {

    private final HigherLowerService higherLowerService = new HigherLowerService();

    final String guessRight = "23";
    final String guessLow = "21";
    final String guessHigh = "25";
    final String testErrorMessage = "Generated number is out of bounds: ";

    @Test
    void randomNumberGenerator() {
        int result = higherLowerService.randomNumberGenerator();
        for (int i = 0; i < 1000; i++) {

            assertTrue(result >= 0 && result <= 100, testErrorMessage + result);
        }
    }

    @Test
    void controlGuess() {
        int result1 = higherLowerService.controlGuess(guessRight, 23);
        int result2 = higherLowerService.controlGuess(guessLow, 23);
        int result3 = higherLowerService.controlGuess(guessHigh, 23);
        assertEquals(0, result1, testErrorMessage + result1);
        assertEquals(1, result2, testErrorMessage + result2);
        assertEquals(2, result3, testErrorMessage + result3);

    }
}