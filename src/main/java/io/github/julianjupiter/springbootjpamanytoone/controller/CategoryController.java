package io.github.julianjupiter.springbootjpamanytoone.controller;

import io.github.julianjupiter.springbootjpamanytoone.domain.Category;
import io.github.julianjupiter.springbootjpamanytoone.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {
    @Value("${page.categories}")
    private String pageName;

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"", "/"})
    public String findAll(Model model) {
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("pageName", pageName);
        model.addAttribute("categories", categories);

        return "category/findAll";
    }
}
