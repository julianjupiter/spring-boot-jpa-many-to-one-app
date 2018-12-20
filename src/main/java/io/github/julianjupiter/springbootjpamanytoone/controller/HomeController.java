package io.github.julianjupiter.springbootjpamanytoone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/home"})
    public String index(Model model) {
        return "home";
    }
}
