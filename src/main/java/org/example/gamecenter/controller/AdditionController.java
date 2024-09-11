package org.example.gamecenter.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdditionController {

    @GetMapping("/addition")
    public String addition() {
        System.out.println("addition() method is called");
        return "addition";
    }
}
