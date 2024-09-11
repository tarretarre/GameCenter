package org.example.gamecenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OddOrEven {

    @GetMapping("/oddOrEven")
    public String oddOrEvenGame() {
        return "oddOrEven";
    }
}
