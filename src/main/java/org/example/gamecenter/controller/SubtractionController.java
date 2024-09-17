package org.example.gamecenter.controller;

import jakarta.servlet.http.HttpSession;
import org.example.gamecenter.service.SubtractionGameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class SubtractionController {

    private final SubtractionGameService service;

    public SubtractionController(SubtractionGameService service) {
        this.service = service;
    }

        @GetMapping("/minus")
        public String minus(Model model, HttpSession session) {
            Map<String, Object> questionAndAnswerData = service.gameLogic();
            session.setAttribute("roundCounter", 0);


            if(questionAndAnswerData.isEmpty()){
                return "minus";
            }
            model.addAttribute("question", questionAndAnswerData.get("question"));
            model.addAttribute("answer", questionAndAnswerData.get("answer"));
            model.addAttribute("roundCounter",session.getAttribute("roundCounter"));
            model.addAttribute("endGame", questionAndAnswerData.get("endGame"));
            return "minus";
        }

      /* @GetMapping("/resetGame")
        public String resetGame(HttpSession session){
           session.invalidate();
            return "redirect:/minus";
        }*/



        @PostMapping("/checkAnswer")
        public String checkAnswer(@RequestParam ("selectedAnswer") String selectedAnswer, HttpSession session, Model model) {
            Integer roundCounter = (Integer) session.getAttribute("roundCounter");
            Integer correctAnswerCounter = (Integer) session.getAttribute("correctAnswerCounter");


            if(roundCounter == null){
                roundCounter = 0;
            }
            roundCounter++;


            if(correctAnswerCounter == null){
                correctAnswerCounter = 0;
                session.setAttribute("correctAnswerCounter", correctAnswerCounter);
            }


            int userAnswer;
            try{
                userAnswer = Integer.parseInt(selectedAnswer);
            }
            catch (NumberFormatException e){
                e.printStackTrace();
                return "redirect:/minus";
            }


            Map<String, Object> checkAnswerMap = service .checkAnswer(userAnswer,session);

            correctAnswerCounter = (Integer) session.getAttribute("correctAnswerCounter");
            session.setAttribute("roundCounter", roundCounter);
            int endGame = (int)checkAnswerMap.get("endGame");










            System.out.println("Session attributes: roundCounter = " + roundCounter + " CorrectanswerCounter = "+ correctAnswerCounter);



            if (roundCounter > endGame){
                session.setAttribute("correctAnswerCounter",0);
                session.setAttribute("roundCounter",0);
                return "redirect:/minus";
            }

            session.setAttribute("roundCounter",roundCounter);
            session.setAttribute("correctAnswerCounter", correctAnswerCounter);


            Map<String, Object> questionAndAnswerData = service.gameLogic();
            model.addAttribute("endGame",endGame);
            model.addAttribute("question", questionAndAnswerData.get("question"));
            model.addAttribute("answer", questionAndAnswerData.get("answer"));
            model.addAttribute("roundCounter",roundCounter);
            model.addAttribute("correctAnswerCounter", correctAnswerCounter);
            return "minus";
        }

}
