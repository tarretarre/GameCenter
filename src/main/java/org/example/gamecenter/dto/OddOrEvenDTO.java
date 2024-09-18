package org.example.gamecenter.dto;

import lombok.Data;

@Data
public class OddOrEvenDTO {
    private int randomNumber;
    private int correctChoices = 0;
    private int roundCount = 0;

    public void resetGame() {
        correctChoices = 0;
        roundCount = 0;
    }
}
