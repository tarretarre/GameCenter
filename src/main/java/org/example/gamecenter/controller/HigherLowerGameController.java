package org.example.gamecenter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class HigherLowerGameController {
    @GetMapping("/higherlowergame")
    public String higherLowerGame(Model model) {
        if (!model.containsAttribute("startGame")) {
            model.addAttribute("startGame", false);
        }
        return "HigherLowerGame";
    }

    @GetMapping("startTheGame")
    public String startTheGameButton(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("startGame", true);
        return "redirect:/higherlowergame";
    }

    @PostMapping("makeGuess")
    public String makeGuess(@RequestParam("guess") String guess, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("startGame", true);
        redirectAttributes.addFlashAttribute("guess", guess);
        System.out.println(guess);
        return "redirect:/higherlowergame";
    }
}
