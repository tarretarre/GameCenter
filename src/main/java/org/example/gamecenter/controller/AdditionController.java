package org.example.gamecenter.controller;

import lombok.RequiredArgsConstructor;
import org.example.gamecenter.dto.AdditionGameDto;
import org.example.gamecenter.service.AdditionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdditionController {
    private final AdditionService additionService;

    @GetMapping("/addition")
    public String addition(Model model) {
        if(!model.containsAttribute("gameDto")) {
            model.addAttribute("gameDto", new AdditionGameDto());
        }

        if(!model.containsAttribute("message")) {
            model.addAttribute("message", "");
        }

        return "addition";
    }

    @PostMapping("/addition")
    public String additionPost(AdditionGameDto gameDto, RedirectAttributes rda) {
        if(!gameDto.isStarted()) {
            additionService.startGame(gameDto);
            rda.addFlashAttribute("gameDto", gameDto);
            return "redirect:/addition";
        }

        additionService.nextRound(gameDto);
        rda.addFlashAttribute("gameDto", gameDto);
        return "redirect:/addition";
    }

    @PostMapping("/addition/answer_check")
    public String answerCheck(AdditionGameDto gameDto, RedirectAttributes rda) {
        additionService.checkAnswer(gameDto);
        rda.addFlashAttribute("gameDto", gameDto);
        return "redirect:/addition";
    }

}
