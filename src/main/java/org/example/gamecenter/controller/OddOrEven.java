package org.example.gamecenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OddOrEven {

    @GetMapping("/oddOrEven")
    public String oddOrEvenGame() {
        System.out.println("Game Odd or Even opened");
        return "oddOrEven";
    }
}
