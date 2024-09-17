package org.example.gamecenter.service;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SubtractionGameService {
    public HashMap<String,Integer> questionAndAnswer = new HashMap<>();
    public Map<String, Object> gameResult = new HashMap<>();

    public SubtractionGameService() {
        questionAndAnswer.put("20-10",10);
        questionAndAnswer.put("30-15",15);
        questionAndAnswer.put("5-2",3);
        questionAndAnswer.put("100-70",30);
        questionAndAnswer.put("16-8",8);
        questionAndAnswer.put("20-20",0);
        questionAndAnswer.put("18-4",14);
        questionAndAnswer.put("25-10",15);

    }

    public Map<String, Object> gameLogic(){
        int endGame = 5;
        int roundCounter = (int) gameResult.getOrDefault("roundCounter",1);

        if(roundCounter >= endGame){
            gameResult.put("endGame",endGame);
            return gameResult;
        }

            Random random = new Random();
            Object[] questions = questionAndAnswer.keySet().toArray();
            String question = (String)questions[random.nextInt(questions.length)];
            List<Integer> answer = new ArrayList<>();
            int correctAnswer = questionAndAnswer.get(question);

            answer.add(correctAnswer);

            while(answer.size() < 3){
                int inCorrectAnswer = random.nextInt(100)+1;
                if(inCorrectAnswer!= correctAnswer && !answer.contains(inCorrectAnswer)){
                    answer.add(inCorrectAnswer);

                }

            }


            Collections.shuffle(answer);

            gameResult.put("question",question);
            gameResult.put("answer",answer);
            gameResult.put("roundCounter",roundCounter);
            gameResult.put("endGame",endGame);
            gameResult.put("correctAnswer", correctAnswer);
        System.out.println("Gamelogic: "+gameResult);





        return gameResult;

    }

    public Map<String, Object> checkAnswer(int userAnswer, HttpSession session){
        Integer correctAnswer = (Integer) gameResult.get("correctAnswer");
        if(correctAnswer == null){
            throw new IllegalStateException("Correct answer not set in gameresult");
        }

        Integer correctAnswerCounter = (Integer) session.getAttribute("correctAnswerCounter");
        if(correctAnswerCounter == null){
            correctAnswerCounter = 0;
        }
        if(userAnswer == correctAnswer){
            correctAnswerCounter++;
            session.setAttribute("correctAnswerCounter",correctAnswerCounter);
        }


        System.out.println("Updated gameresult: " + gameResult);

        return gameResult;
    }




}
