package org.example.gamecenter.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.gamecenter.dto.AdditionGameDto;
import org.example.gamecenter.service.AdditionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/addition")
public class AdditionController {
    private final AdditionService additionService;

    @GetMapping()
    public String addition(Model model, HttpSession session) {
        AdditionGameDto gameDto = getOrCreateGameDto(session);
        model.addAttribute("gameDto", gameDto);

        addDefaultModelAttributes(model);
        return "addition";
    }

    @PostMapping()
    public String additionPost(Integer totalRounds, HttpSession session) {
        AdditionGameDto gameDto = getOrCreateGameDto(session);

        if (additionService.isFirstRound(gameDto)) {
            startNewGame(gameDto, totalRounds, session);
        } else {
            proceedToNextRound(gameDto, session);
        }

        return "redirect:/addition";
    }

    @PostMapping("/answer-check")
    public String answerCheck(@RequestParam Integer userAnswer, RedirectAttributes rda, HttpSession session) {
        AdditionGameDto gameDto = getOrCreateGameDto(session);

        if (additionService.isLastRound(gameDto)) {
            rda.addFlashAttribute("lastRound", true);
        }

        gameDto.setUserAnswer(userAnswer);
        additionService.checkAnswer(gameDto);

        session.setAttribute("gameDto", gameDto);
        rda.addFlashAttribute("answerCheck", true);
        return "redirect:/addition";
    }

    @PostMapping("/game-over")
    public String gameOver(RedirectAttributes rda) {
        rda.addFlashAttribute("gameOver", true);
        return "redirect:/addition";
    }

    @PostMapping("/reset")
    public String reset(HttpSession session) {
        session.removeAttribute("gameDto");
        return "redirect:/addition";
    }

    private AdditionGameDto getOrCreateGameDto(HttpSession session) {
        AdditionGameDto gameDto = (AdditionGameDto) session.getAttribute("gameDto");

        if (gameDto == null) {
            gameDto = new AdditionGameDto();
            session.setAttribute("gameDto", gameDto);
        }

        return gameDto;
    }

    private void addDefaultModelAttributes(Model model) {
        if (!model.containsAttribute("answerCheck")) {
            model.addAttribute("answerCheck", false);
        }

        if (!model.containsAttribute("lastRound")) {
            model.addAttribute("lastRound", false);
        }

        if (!model.containsAttribute("gameOver")) {
            model.addAttribute("gameOver", false);
        }
    }

    private void startNewGame(AdditionGameDto gameDto, Integer totalRound, HttpSession session) {
        try {
            additionService.startGame(gameDto, totalRound);
            session.setAttribute("gameDto", gameDto);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void proceedToNextRound(AdditionGameDto gameDto, HttpSession session) {
        try {
            additionService.nextRound(gameDto);
            session.setAttribute("gameDto", gameDto);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
