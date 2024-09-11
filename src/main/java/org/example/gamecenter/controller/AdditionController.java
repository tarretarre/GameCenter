package org.example.gamecenter.controller;
import org.example.gamecenter.dto.AdditionGameDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdditionController {

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
}
