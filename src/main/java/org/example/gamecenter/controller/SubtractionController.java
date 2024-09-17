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



        @PostMapping("/checkAnswer")
        public String checkAnswer(@RequestParam ("selectedAnswer") String selectedAnswer, HttpSession session, Model model) {
            Map<String, Object> questionAndAnswerData = service .gameLogic();
            Integer roundCounter = (Integer) session.getAttribute("roundCounter");
            int endGame = (int)questionAndAnswerData.get("endGame");

            if(roundCounter == null){
                roundCounter = 0;
            }
            else{
                roundCounter++;
            }
            session.setAttribute("roundCounter", roundCounter);



            if (roundCounter > endGame){
                return "redirect:/minus";
            }


            model.addAttribute("endGame",endGame);
            model.addAttribute("question", questionAndAnswerData.get("question"));
            model.addAttribute("answer", questionAndAnswerData.get("answer"));
            model.addAttribute("roundCounter",roundCounter);
            return "minus";
        }

}
