package org.example.gamecenter.controller;

import lombok.RequiredArgsConstructor;
import org.example.gamecenter.service.HigherLowerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;


@RequiredArgsConstructor
@Controller
public class HigherLowerGameController {

    private final HigherLowerService higherLowerService;

    @GetMapping("/higherlowergame")
    public String higherLowerGame(Model model) {
        if (!model.containsAttribute("startGame")) {
            model.addAttribute("startGame", false);
        }
        if (!model.containsAttribute("result")) {
            model.addAttribute("result", false);
        }
        return "HigherLowerGame";
    }

    @GetMapping("startTheGame")
    public String startTheGameButton(RedirectAttributes redirectAttributes, HttpSession session) {
        redirectAttributes.addFlashAttribute("startGame", true);
        int guessInt = higherLowerService.randomNumberGenerator();
        session.setAttribute("guessInt", guessInt);
        return "redirect:/higherlowergame";
    }

    @PostMapping("makeGuess")
    public String makeGuess(@RequestParam("guess") String guess, RedirectAttributes redirectAttributes, HttpSession session) {
        redirectAttributes.addFlashAttribute("startGame", true);
        redirectAttributes.addFlashAttribute("guess", guess);
        Integer guessInt = (Integer) session.getAttribute("guessInt");

        if (guessInt != null) {
            int result = higherLowerService.controlGuess(guess, guessInt);
            redirectAttributes.addFlashAttribute("result", result);
        } else {
            redirectAttributes.addFlashAttribute("error", "Game has not been started or session expired.");
        }
        return "redirect:/higherlowergame";
    }
}
