package io.github.julianjupiter.springbootjpamanytoone.controller;

import io.github.julianjupiter.springbootjpamanytoone.domain.Book;
import io.github.julianjupiter.springbootjpamanytoone.domain.Category;
import io.github.julianjupiter.springbootjpamanytoone.exception.ResourceNotFoundException;
import io.github.julianjupiter.springbootjpamanytoone.form.BookForm;
import io.github.julianjupiter.springbootjpamanytoone.service.BookService;
import io.github.julianjupiter.springbootjpamanytoone.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController extends BaseController {
    @Value("${page.books}")
    private String pageName;

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("pageName", pageName);
    }

    @GetMapping
    public String findAll(Model model, @ModelAttribute("message") String message, @RequestParam(value = "category", required = false) String category) {
        model.addAttribute("pageNameSuffix", "View All");

        if (category != null && !category.isEmpty()) {
            model.addAttribute("books", bookService.findByCategoryName(category));
        } else {
            model.addAttribute("books", bookService.findAll());
        }

        return "book/findAll";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable long id, Model model) throws ResourceNotFoundException {
        return bookService.findById(id)
                .map(book -> {
                    model.addAttribute("pageNameSuffix", "View");
                    model.addAttribute("book", book);
                    return "book/view";
                })
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    @GetMapping("/create")
    public String create(Book book, Model model) {
        model.addAttribute("pageNameSuffix", "Create");

        return "book/create";
    }

    @PostMapping("/create")
    public String create(@Valid Book book, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageNameSuffix", "Create");

            return "book/create";
        }

        bookService.save(book);

        redirectAttributes.addFlashAttribute("message", "Book has been created!");

        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model) throws ResourceNotFoundException {
        return bookService.findById(id)
                .map(book -> {
                    model.addAttribute("pageNameSuffix", "Edit");
                    model.addAttribute("book", book);
                    return "book/edit";
                })
                .orElseThrow(() -> new ResourceNotFoundException());
    }


    @PostMapping("/edit/{id}")
    public String update(@PathVariable long id, @Valid Book book, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageNameSuffix", "Edit");

            return "book/edit";
        }

        return bookService.findById(book.getId())
                .map(existingBook -> {
                    bookService.save(book);
                    redirectAttributes.addFlashAttribute("message", "Book with ID " + book.getId() + " has been updated!");
                    return "redirect:/books";
                })
                .orElseThrow(() -> new ResourceNotFoundException());
    }
}
