package io.github.julianjupiter.springbootjpamanytoone.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
    @Value("${app.name}")
    private String appName;

    @ModelAttribute
    public void addCommonObjects(Model model) {
        model.addAttribute("appName", appName);
    }
}
