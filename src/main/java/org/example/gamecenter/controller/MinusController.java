package org.example.gamecenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MinusController {
    @GetMapping("/minus")
    public String minus() {
        return "minus";
    }
}
