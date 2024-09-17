package org.example.gamecenter.service;

import org.example.gamecenter.dto.AdditionGameDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class AdditionService {

    public void startGame(AdditionGameDto gameDto, Integer totalRounds) {
        if(totalRounds == null || totalRounds < 1) {
            throw new IllegalArgumentException("Start Game -> Total Rounds cannot be null or less than 1");
        }

        gameDto.setTotalRounds(totalRounds);
        generateQuestionsAndAnswers(gameDto);
        generateAnswerChoices(gameDto);
        gameDto.setStarted(true);
    }

    public void nextRound(AdditionGameDto gameDto) {
        gameDto.setCurrentRound(gameDto.getCurrentRound() + 1);
    }

    public void checkAnswer(AdditionGameDto gameDto) {
        if(gameDto.getAnswers().size() <= gameDto.getCurrentRound() || gameDto.getAnswers().isEmpty()) {
            throw new IllegalArgumentException("Check Answer -> Answers cannot be empty or less than current round");
        }

        if (gameDto.getUserAnswer() == null) {
            throw new IllegalArgumentException("Check Answer -> User Answer cannot be null");
        }

        int currentRound = gameDto.getCurrentRound();
        int userAnswer = gameDto.getUserAnswer();
        int correctAnswer = gameDto.getAnswers().get(currentRound);
        if (userAnswer == correctAnswer) {
            gameDto.setCorrectAnswers(gameDto.getCorrectAnswers() + 1);
        }
    }

    public void generateQuestionsAndAnswers(AdditionGameDto gameDto) {
        if(gameDto.getTotalRounds() == null || gameDto.getTotalRounds() < 1) {
            throw new IllegalArgumentException("Generate Questions And Answers -> Total Rounds cannot be null or less than 1");
        }

        Random random = new Random();
        for (int i = 0; i < gameDto.getTotalRounds(); i++) {
            int num1 = random.nextInt(100);
            int num2 = random.nextInt(100);
            gameDto.getQuestions().add(num1 + " + " + num2 + " = ?");
            gameDto.getAnswers().add(num1 + num2);
        }
    }

    public void generateAnswerChoices(AdditionGameDto gameDto) {
        if(gameDto.getAnswers() == null || gameDto.getAnswers().isEmpty()) {
            throw new IllegalArgumentException("Generate Answer Choices -> Answers cannot be null or empty");
        }

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

    public boolean isLastRound(AdditionGameDto gameDto) {
        return gameDto.getCurrentRound() == gameDto.getTotalRounds() - 1;
    }

    public boolean isFirstRound(AdditionGameDto gameDto) {
        return !gameDto.isStarted();
    }
}
