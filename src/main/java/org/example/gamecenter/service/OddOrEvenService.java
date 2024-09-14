package org.example.gamecenter.service;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Random;

@Data
@Service
@Configuration
public class OddOrEvenService {

    private Random random = new Random();
    private int randomNumber;
    private int correctChoices = 0;
    private int roundCount = 0;

    public void generateRandomNumber() {
        randomNumber = random.nextInt(100) + 1;
    }

    public void startNewGame() {
        resetGame();
        generateRandomNumber();
    }

    private void resetGame() {
        correctChoices = 0;
        roundCount = 0;
    }

    public boolean checkChoice(String choice) {
        boolean isEven = (randomNumber % 2 == 0);
        return (isEven && choice.equalsIgnoreCase("Even")) || (!isEven && choice.equalsIgnoreCase("Odd"));
    }

    public void updateScore(boolean correctChoice) {
        if (correctChoice) {
            correctChoices++;
        }
    }

    public String getRoundResult(boolean correctChoice) {
        return correctChoice ? "Correct" : "Wrong";
    }

    public String getFinalResult() {
        return "You got " + correctChoices + " right out of 5!";
    }

    public boolean isGameOver() {
        return roundCount >= 5;
    }

    public String playRound(String choice) {
        roundCount++;

        boolean isCorrectChoice = checkChoice(choice);
        updateScore(isCorrectChoice);

        String result;
        if (isGameOver()) {
            result = getFinalResult();
        } else {
            result = getRoundResult(isCorrectChoice);
            generateRandomNumber();
        }

        return result;
    }
}
