package io.github.julianjupiter.springbootjpamanytoone.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController {
    @Value("${page.home}")
    private String pageName;

    @GetMapping({"/", "/home"})
    public String index(Model model) {
        model.addAttribute("pageName", pageName);

        return "home";
    }
}
