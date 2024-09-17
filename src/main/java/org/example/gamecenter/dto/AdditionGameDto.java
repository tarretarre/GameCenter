package org.example.gamecenter.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class AdditionGameDto {
    private Integer totalRounds;
    private int currentRound;
    private int correctAnswers;
    private Integer userAnswer;
    private List<String> questions;
    private List<Integer> answers;
    private List<List<Integer>> answerChoices;
    private boolean started;


    public AdditionGameDto() {
        this.totalRounds = 0;
        this.currentRound = 0;
        this.correctAnswers = 0;
        this.userAnswer = null;
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.answerChoices = new ArrayList<>();
        this.started = false;
    }
}
