package org.example.gamecenter.service;

import org.example.gamecenter.DTO.OddOrEvenDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OddOrEvenServiceTest {

    @Autowired
    private OddOrEvenService oddOrEvenService;

    private OddOrEvenDTO ooeDTO;

    @BeforeEach
    public void setup() {
        ooeDTO = new OddOrEvenDTO();
    }

    @Test
    public void testGenerateRandomNumberWithinRange() {
        oddOrEvenService.generateRandomNumber(ooeDTO);
        int randomNumber = ooeDTO.getRandomNumber();
        assertTrue(randomNumber >= 1 && randomNumber <= 100);
    }

    @Test
    public void testStartNewGameShouldResetGameAndGenerateRandomNumber() {
        oddOrEvenService.startNewGame(ooeDTO);
        assertEquals(0, ooeDTO.getCorrectChoices());
        assertEquals(0, ooeDTO.getRoundCount());
        assertTrue(ooeDTO.getRandomNumber() > 0);
    }

    @Test
    public void testCheckEvenChoice() {
        ooeDTO.setRandomNumber(12);
        assertTrue(oddOrEvenService.checkChoice(ooeDTO, "even"));
    }

    @Test
    public void testCheckOddChoice() {
        ooeDTO.setRandomNumber(11);
        assertTrue(oddOrEvenService.checkChoice(ooeDTO, "odd"));
    }

    @Test
    public void testUpdateScoreIncreaseOnCorrectChoice() {
        oddOrEvenService.updateScore(ooeDTO,true);
        assertEquals(1, ooeDTO.getCorrectChoices());
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
        ooeDTO.setCorrectChoices(3);
        String result = oddOrEvenService.getFinalResult(ooeDTO);
        assertEquals("You got 3 right out of 5!", result);
    }

    @Test
    public void testIsGameOverAfterFiveRounds() {
        oddOrEvenService.startNewGame(ooeDTO);

        for (int i = 0; i < 5; i++) {
            oddOrEvenService.playRound(ooeDTO, "even");
        }

        assertTrue(oddOrEvenService.isGameOver(ooeDTO));
    }
}