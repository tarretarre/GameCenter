package org.example.gamecenter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OddOrEvenServiceTest {

    @Autowired
    private OddOrEvenService oddOrEvenService;

    @Test
    public void testGenerateRandomNumberWithinRange() {
        oddOrEvenService.generateRandomNumber();
        int randomNumber = oddOrEvenService.getRandomNumber();
        assertTrue(randomNumber >= 1 && randomNumber <= 100);
    }

    @Test
    public void testStartNewGameShouldResetGameAndGenerateRandomNumber() {
        oddOrEvenService.startNewGame();
        assertEquals(0, oddOrEvenService.getCorrectChoices());
        assertEquals(0, oddOrEvenService.getRoundCount());
        assertTrue(oddOrEvenService.getRandomNumber() > 0);
    }

    @Test
    public void testCheckEvenChoice() {
        oddOrEvenService.setRandomNumber(12);
        assertTrue(oddOrEvenService.checkChoice("even"));
    }

    @Test
    public void testCheckOddChoice() {
        oddOrEvenService.setRandomNumber(11);
        assertTrue(oddOrEvenService.checkChoice("odd"));
    }


}