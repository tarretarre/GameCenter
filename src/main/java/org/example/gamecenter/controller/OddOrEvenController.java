package org.example.gamecenter.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.gamecenter.dto.OddOrEvenDTO;
import org.example.gamecenter.service.OddOrEvenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/oddOrEven")
public class OddOrEvenController {

    private final OddOrEvenService ooe;

    @GetMapping("")
    public String oddOrEvenGame() {
        return "oddOrEven";
    }

    @GetMapping("/Start")
    public String startGame(Model model, HttpSession session) {
        OddOrEvenDTO ooeDTO = new OddOrEvenDTO();
        ooe.startNewGame(ooeDTO);
        session.setAttribute("ooeDTO", ooeDTO);
        model.addAttribute("randomNumber", ooeDTO.getRandomNumber());
        return "oddOrEvenGamePlay";
    }

    @PostMapping("/Gameplay")
    public String gamePlay(@RequestParam("choice") String choice, RedirectAttributes redirectAttributes, HttpSession session) {
        OddOrEvenDTO ooeDTO = (OddOrEvenDTO) session.getAttribute("ooeDTO");

        if (ooeDTO == null) {
            return "redirect:/oddOrEven/Start";
        }

        String result = ooe.playRound(ooeDTO, choice);
        redirectAttributes.addFlashAttribute("result", result);

        if (ooe.isGameOver(ooeDTO)) {
            redirectAttributes.addFlashAttribute("correctChoices", ooeDTO.getCorrectChoices());
            return "redirect:/oddOrEven/GameOver";
        }

        redirectAttributes.addFlashAttribute("randomNumber", ooeDTO.getRandomNumber());
        return "redirect:/oddOrEven/Gameplay";
    }

    @GetMapping("/Gameplay")
    public String showPlayPage() {
        return "oddOrEvenGamePlay";
    }

    @GetMapping("/GameOver")
    public String gameOver(Model model, HttpSession session) {
        OddOrEvenDTO ooeDTO = (OddOrEvenDTO) session.getAttribute("ooeDTO");
        if (ooeDTO != null) {
            model.addAttribute("correctChoices", ooeDTO.getCorrectChoices());
        }
        return "oddOrEvenGameOver";
    }
}
