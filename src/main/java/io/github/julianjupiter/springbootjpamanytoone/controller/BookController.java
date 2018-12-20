package io.github.julianjupiter.springbootjpamanytoone.controller;

import io.github.julianjupiter.springbootjpamanytoone.domain.Book;
import io.github.julianjupiter.springbootjpamanytoone.exception.ResourceNotFoundException;
import io.github.julianjupiter.springbootjpamanytoone.service.BookService;
import io.github.julianjupiter.springbootjpamanytoone.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public void commonAttributes(Model model) {
        model.addAttribute("categories", categoryService.findAll());
    }

    @GetMapping
    public String findAll(Model model, @ModelAttribute("message") String message, @RequestParam(value = "category", required = false) String category) {
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
                    model.addAttribute("book", book);
                    return "book/view";
                })
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    @GetMapping("/create")
    public String create(Book book, Model model) {
        return "book/create";
    }

    @PostMapping("/create")
    public String create(@Valid Book book, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
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
                    model.addAttribute("book", book);
                    return "book/edit";
                })
                .orElseThrow(() -> new ResourceNotFoundException());
    }


    @PostMapping("/edit/{id}")
    public String update(@PathVariable long id, @Valid Book book, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
        if (bindingResult.hasErrors()) {
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

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id, Model model) throws ResourceNotFoundException {
        return bookService.findById(id)
                .map(book -> {
                    model.addAttribute("book", book);
                    return "book/delete";
                })
                .orElseThrow(() -> new ResourceNotFoundException());
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        bookService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Book with ID " + id + " has been deleted!");
        return "redirect:/books";
    }
}
