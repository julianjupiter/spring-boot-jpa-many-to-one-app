package io.github.julianjupiter.springbootjpamanytoone.controller;

import io.github.julianjupiter.springbootjpamanytoone.domain.Category;
import io.github.julianjupiter.springbootjpamanytoone.exception.ResourceNotFoundException;
import io.github.julianjupiter.springbootjpamanytoone.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping({"", "/"})
    public String findAll(Model model) {
        Iterable<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "category/findAll";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable long id, Model model) throws ResourceNotFoundException {
        return categoryService.findById(id)
                .map(category -> {
                    model.addAttribute("category", category);
                    return "category/view";
                })
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    @GetMapping("/create")
    public String create(Category category, Model model) {
        return "category/create";
    }

    @PostMapping("/create")
    public String create(@Valid Category category, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "category/create";
        }

        categoryService.save(category);

        redirectAttributes.addFlashAttribute("message", "Category has been created!");

        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model) throws ResourceNotFoundException {
        return categoryService.findById(id)
                .map(category -> {
                    model.addAttribute("category", category);
                    return "category/edit";
                })
                .orElseThrow(() -> new ResourceNotFoundException());
    }


    @PostMapping("/edit/{id}")
    public String update(@PathVariable long id, @Valid Category category, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
        if (bindingResult.hasErrors()) {
            return "category/edit";
        }

        return categoryService.findById(category.getId())
                .map(existingBook -> {
                    categoryService.save(category);
                    redirectAttributes.addFlashAttribute("message", "Category with ID " + category.getId() + " has been updated!");
                    return "redirect:/categories";
                })
                .orElseThrow(() -> new ResourceNotFoundException());
    }
}
