package org.example.gamecenter.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class AdditionGameDto {
    private int totalRounds;
    private int currentRound;
    private int correctAnswers;
    private int userAnswer;
    private List<String> questions;
    private List<Integer> answers;
    private List<List<Integer>> answerChoices;
    private boolean started;


    public AdditionGameDto() {
        this.totalRounds = 0;
        this.currentRound = 0;
        this.correctAnswers = 0;
        this.userAnswer = -1;
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.answerChoices = new ArrayList<>();
        this.started = false;
    }

    public void print() {
        System.out.println("Total Rounds: " + totalRounds);
        System.out.println("Current Round: " + currentRound);
        System.out.println("Correct Answers: " + correctAnswers);
        System.out.println("User Answer: " + userAnswer);
        System.out.println("Questions: " + questions);
        System.out.println("Answers: " + answers);
        System.out.println("Answer Choices: " + answerChoices);
        System.out.println("Started: " + started);
    }
}
