package org.example.gamecenter.service;

import org.example.gamecenter.dto.AdditionGameDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdditionServiceTest {
    AdditionService additionService = new AdditionService();
    @Test
    void emptyDtoStartGame_shouldGenerateQuestionAndAnswers_andSetGameToStarted() {
        AdditionGameDto emptyDto = new AdditionGameDto();
        Integer totalRounds = 5;
        additionService.startGame(emptyDto, totalRounds);

        assertEquals(totalRounds, emptyDto.getTotalRounds());
        assertEquals(5, emptyDto.getQuestions().size());
        assertEquals(5, emptyDto.getAnswers().size());
        assertEquals(5, emptyDto.getAnswerChoices().size());
        for(List<Integer> choices : emptyDto.getAnswerChoices()) {
            assertEquals(4, choices.size());
        }
        assertTrue(emptyDto.isStarted());
    }

    @Test
    void nextRound_shouldIncrementCurrentRound() {
        AdditionGameDto emptyDto = new AdditionGameDto();
        AdditionGameDto populatedDto = new AdditionGameDto();
        populatedDto.setCurrentRound(3);

        additionService.nextRound(emptyDto);
        additionService.nextRound(populatedDto);
        assertEquals(1, emptyDto.getCurrentRound());
        assertEquals(4, populatedDto.getCurrentRound());
    }

    @Test
    void checkAnswer_shouldIncrementCorrectAnswers_ifUserAnswerIsCorrect() {
        AdditionGameDto correctAnswer = new AdditionGameDto();
        correctAnswer.setCurrentRound(0);
        correctAnswer.setUserAnswer(5);
        correctAnswer.getAnswers().add(5);
        correctAnswer.setCorrectAnswers(0);

        additionService.checkAnswer(correctAnswer);
        assertEquals(1, correctAnswer.getCorrectAnswers());
    }

    @Test
    void checkAnswer_shouldNotIncrementCorrectAnswers_ifUserAnswerIsIncorrect() {
        AdditionGameDto incorrectAnswer = new AdditionGameDto();
        incorrectAnswer.setCurrentRound(0);
        incorrectAnswer.setUserAnswer(5);
        incorrectAnswer.getAnswers().add(6);
        incorrectAnswer.setCorrectAnswers(0);

        additionService.checkAnswer(incorrectAnswer);
        assertEquals(0, incorrectAnswer.getCorrectAnswers());
    }

    @Test
    void generateQuestionsAndAnswers_shouldGenerateListWithQuestions_andListWithCorrectAnswers() {
        AdditionGameDto gameDto = new AdditionGameDto();
        gameDto.setTotalRounds(5);
        additionService.generateQuestionsAndAnswers(gameDto);

        assertEquals(5, gameDto.getQuestions().size());
        assertEquals(5, gameDto.getAnswers().size());

        for (int i = 0; i < gameDto.getQuestions().size() ; i++) {
            String question = gameDto.getQuestions().get(i);
            int answer = gameDto.getAnswers().get(i);
            String[] parts = question.split(" ");
            int num1 = Integer.parseInt(parts[0]);
            int num2 = Integer.parseInt(parts[2]);
            assertEquals(answer, num1 + num2);
        }

    }

    @Test
    void generateAnswerChoices_shouldGenerateListWithOneCorrectAnswer_andThreeWrongAnswers() {
        AdditionGameDto gameDto = new AdditionGameDto();
        gameDto.setTotalRounds(5);
        additionService.generateQuestionsAndAnswers(gameDto);
        additionService.generateAnswerChoices(gameDto);

        assertEquals(5, gameDto.getAnswerChoices().size());

        for (int i = 0; i < gameDto.getAnswerChoices().size(); i++) {
            List<Integer> choices = gameDto.getAnswerChoices().get(i);
            assertEquals(4, choices.size());

            Integer correctAnswer = gameDto.getAnswers().get(i);
            assertTrue(choices.contains(correctAnswer));

            choices.remove(correctAnswer);
            assertEquals(3, choices.size());

            for (int j = 1; j < choices.size(); j++) {
                assertNotEquals(correctAnswer, choices.get(j));
            }
        }
    }

    @Test
    void isLastRound_shouldReturnTrue_ifGameWillBeRunningLastRound() {
        AdditionGameDto correct = new AdditionGameDto();
        correct.setTotalRounds(5);
        correct.setCurrentRound(4);

        AdditionGameDto incorrect = new AdditionGameDto();
        incorrect.setTotalRounds(5);
        incorrect.setCurrentRound(3);
        assertTrue(additionService.isLastRound(correct));
        assertFalse(additionService.isLastRound(incorrect));
    }

    @Test
    void isFirstRound_shouldReturnTrue_ifGameHasNotStarted() {
        AdditionGameDto correct = new AdditionGameDto();
        correct.setStarted(false);

        AdditionGameDto incorrect = new AdditionGameDto();
        incorrect.setStarted(true);

        assertTrue(additionService.isFirstRound(correct));
        assertFalse(additionService.isFirstRound(incorrect));
    }
}