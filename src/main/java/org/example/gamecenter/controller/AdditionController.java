package org.example.gamecenter.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.gamecenter.dto.AdditionGameDto;
import org.example.gamecenter.service.AdditionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdditionController {
    private final AdditionService additionService;

    @GetMapping("/addition")
    public String addition(Model model, HttpSession session) {
        AdditionGameDto gameDto = (AdditionGameDto) session.getAttribute("gameDto");

        if (gameDto == null) {
            gameDto = new AdditionGameDto();
            session.setAttribute("gameDto", gameDto);
        }

        model.addAttribute("gameDto", gameDto);

        if (!model.containsAttribute("answerCheck")) {
            model.addAttribute("answerCheck", false);
        }

        if (!model.containsAttribute("lastRound")) {
            model.addAttribute("lastRound", false);
        }

        if(!model.containsAttribute("gameOver")) {
            model.addAttribute("gameOver", false);
        }

        return "addition";
    }

    @PostMapping("/addition")
    public String additionPost(Integer totalRounds, HttpSession session) {
        AdditionGameDto gameDto = (AdditionGameDto) session.getAttribute("gameDto");

        if (additionService.isFirstRound(gameDto)) {
            additionService.startGame(gameDto, totalRounds);
            session.setAttribute("gameDto", gameDto);
            return "redirect:/addition";
        }

        additionService.nextRound(gameDto);
        session.setAttribute("gameDto", gameDto);
        return "redirect:/addition";
    }

    @PostMapping("/addition/answer_check")
    public String answerCheck(@ModelAttribute(name = "gameDto") AdditionGameDto formDto, RedirectAttributes rda, HttpSession session) {
        AdditionGameDto gameDto = (AdditionGameDto) session.getAttribute("gameDto");

        if(additionService.isLastRound(gameDto)) {
            rda.addFlashAttribute("lastRound", true);
        }

        gameDto.setUserAnswer(formDto.getUserAnswer());
        additionService.checkAnswer(gameDto);

        session.setAttribute("gameDto", gameDto);
        rda.addFlashAttribute("answerCheck", true);
        return "redirect:/addition";
    }

}
