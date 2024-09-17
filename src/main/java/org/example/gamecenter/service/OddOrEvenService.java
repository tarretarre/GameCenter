package org.example.gamecenter.service;

import lombok.Data;
import org.example.gamecenter.DTO.OddOrEvenDTO;
import org.springframework.stereotype.Service;

import java.util.Random;

@Data
@Service
public class OddOrEvenService {

    private Random random = new Random();

    public void generateRandomNumber(OddOrEvenDTO ooeDTO) {
        ooeDTO.setRandomNumber(random.nextInt(100) + 1);
    }

    public void startNewGame(OddOrEvenDTO ooeDTO) {
        ooeDTO.resetGame();
        generateRandomNumber(ooeDTO);
    }

    public boolean checkChoice(OddOrEvenDTO ooeDTO, String choice) {
        boolean isEven = (ooeDTO.getRandomNumber() % 2 == 0);
        return (isEven && choice.equalsIgnoreCase("Even"))
                || (!isEven && choice.equalsIgnoreCase("Odd"));
    }

    public void updateScore(OddOrEvenDTO ooeDTO,boolean correctChoice) {
        if (correctChoice) {
            ooeDTO.setCorrectChoices(ooeDTO.getCorrectChoices() + 1);
        }
    }

    public String getRoundResult(boolean correctChoice) {
        return correctChoice ? "Correct" : "Wrong";
    }

    public String getFinalResult(OddOrEvenDTO ooeDTO) {
        return "You got " + ooeDTO.getCorrectChoices() + " right out of 5!";
    }

    public boolean isGameOver(OddOrEvenDTO ooeDTO) {
        return ooeDTO.getRoundCount() >= 5;
    }

    public String playRound(OddOrEvenDTO ooeDTO,String choice) {
        ooeDTO.setRoundCount(ooeDTO.getRoundCount() + 1);

        boolean isCorrectChoice = checkChoice(ooeDTO, choice);
        updateScore(ooeDTO, isCorrectChoice);

        String result;
        if (isGameOver(ooeDTO)) {
            result = getFinalResult(ooeDTO);
        } else {
            result = getRoundResult(isCorrectChoice);
            generateRandomNumber(ooeDTO);
        }

        return result;
    }
}
