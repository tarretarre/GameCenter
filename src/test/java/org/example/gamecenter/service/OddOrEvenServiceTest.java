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

    @Test
    public void testUpdateScoreIncreaseOnCorrectChoice() {
        oddOrEvenService.updateScore(true);
        assertEquals(1, oddOrEvenService.getCorrectChoices());
    }

    @Test
    public void testRoundResultCorrect() {
        String result = oddOrEvenService.getRoundResult(true);
        assertEquals("Correct", result);
    }

    @Test
    public void testRoundResultWrong() {
        String result = oddOrEvenService.getRoundResult(false);
        assertEquals("Wrong", result);
    }

    @Test
    public void testFinalResultMatchingTotalCorrectChoices() {
        oddOrEvenService.setCorrectChoices(3);
        String result = oddOrEvenService.getFinalResult();
        assertEquals("You got 3 right out of 5!", result);
    }

    @Test
    public void testIsGameOverAfterFiveRounds() {
        oddOrEvenService.startNewGame();

        for (int i = 0; i < 5; i++) {
            oddOrEvenService.playRound("even");
        }

        assertTrue(oddOrEvenService.isGameOver());
    }
}