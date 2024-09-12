package org.example.gamecenter.service;

import org.example.gamecenter.dto.AdditionGameDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class AdditionService {

    public void startGame(AdditionGameDto gameDto) {
        generateQuestionsAndAnswers(gameDto);
        generateAnswerChoices(gameDto);
        gameDto.setStarted(true);
        gameDto.setCurrentRound(1);
    }

    public void nextRound(AdditionGameDto gameDto) {
        gameDto.setCurrentRound(gameDto.getCurrentRound() + 1);
    }

    public void checkAnswer(AdditionGameDto gameDto) {
        int currentRound = gameDto.getCurrentRound();
        int userAnswer = gameDto.getUserAnswer();
        int correctAnswer = gameDto.getAnswers().get(currentRound);
        if (userAnswer == correctAnswer) {
            gameDto.setCorrectAnswers(gameDto.getCorrectAnswers() + 1);
        }
    }

    public void generateQuestionsAndAnswers(AdditionGameDto gameDto) {
        Random random = new Random();
        for (int i = 0; i < gameDto.getTotalRounds(); i++) {
            int num1 = random.nextInt(100);
            int num2 = random.nextInt(100);
            gameDto.getQuestions().add(num1 + " + " + num2 + " = ?");
            gameDto.getAnswers().add(num1 + num2);
        }
    }

    public void generateAnswerChoices(AdditionGameDto gameDto) {
        Random random = new Random();
        for (int i = 0; i < gameDto.getAnswers().size(); i++) {
            List<Integer> choices = new ArrayList<>();
            int correctAnswer = gameDto.getAnswers().get(i);
            choices.add(correctAnswer);
            for (int j = 0; j < 3; j++) {
                int wrongAnswer = random.nextInt(200);
                while (wrongAnswer == correctAnswer) {
                    wrongAnswer = random.nextInt(200);
                }
                choices.add(wrongAnswer);
            }
            gameDto.getAnswerChoices().add(choices);
        }
        gameDto.getAnswerChoices().forEach(Collections::shuffle);
    }
}
