package ru.test.hello.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Value("${diTest.strangeString}")
    private String strangeString;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("strange", strangeString);
        return "home";
    }
}
