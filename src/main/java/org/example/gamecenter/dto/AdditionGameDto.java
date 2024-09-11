package org.example.gamecenter.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data

public class AdditionGameDto {
    private int totalRounds;
    private int currentRound;
    private int correct;
    private int userAnswer;
    private Map<String, Integer> questionsAndCorrectAnswers;
    private List<List<Integer>> answerChoices;

    public AdditionGameDto(int totalRounds) {
        this.totalRounds = totalRounds;
        this.currentRound = 1;
        this.correct = 0;
        this.userAnswer = -1;
        this.questionsAndCorrectAnswers = new HashMap<>();
        this.answerChoices = new ArrayList<>();
    }

    public AdditionGameDto() {
        this.totalRounds = 0;
        this.currentRound = 0;
        this.correct = 0;
        this.userAnswer = -1;
        this.questionsAndCorrectAnswers = new HashMap<>();
        this.answerChoices = new ArrayList<>();
    }
}
