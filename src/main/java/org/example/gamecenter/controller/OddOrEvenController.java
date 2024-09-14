package org.example.gamecenter.controller;

import org.example.gamecenter.service.OddOrEvenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/oddOrEven")
public class OddOrEvenController {

    @Autowired
    private OddOrEvenService ooe;

    @GetMapping("")
    public String oddOrEvenGame() {
        return "oddOrEven";
    }

    @GetMapping("/Start")
    public String startGame(Model model) {
        ooe.startNewGame();
        model.addAttribute("randomNumber", ooe.getRandomNumber());
        return "oddOrEvenGamePlay";
    }

    @PostMapping("/Gameplay")
    public String gamePlay(@RequestParam("choice") String choice, RedirectAttributes redirectAttributes) {
        String result = ooe.playRound(choice);
        redirectAttributes.addFlashAttribute("result", result);

        if (ooe.isGameOver()) {
            redirectAttributes.addFlashAttribute("correctChoices", ooe.getCorrectChoices());
            return "redirect:/oddOrEven/GameOver";
        }

        redirectAttributes.addFlashAttribute("randomNumber", ooe.getRandomNumber());
        return "redirect:/oddOrEven/Gameplay";
    }

    @GetMapping("/Gameplay")
    public String showPlayPage() {
        return "oddOrEvenGamePlay";
    }

    @GetMapping("/GameOver")
    public String gameOver(Model model) {
        model.addAttribute("correctChoices", ooe.getCorrectChoices());
        return "oddOrEvenGameOver";
    }
}
