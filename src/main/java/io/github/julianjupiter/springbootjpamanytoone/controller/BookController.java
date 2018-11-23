package io.github.julianjupiter.springbootjpamanytoone.controller;

import io.github.julianjupiter.springbootjpamanytoone.domain.Book;
import io.github.julianjupiter.springbootjpamanytoone.domain.Category;
import io.github.julianjupiter.springbootjpamanytoone.exception.CustomApiExceptionHandler;
import io.github.julianjupiter.springbootjpamanytoone.exception.ResourceNotFoundException;
import io.github.julianjupiter.springbootjpamanytoone.service.BookService;
import io.github.julianjupiter.springbootjpamanytoone.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping
    public String findAll(Model model, @ModelAttribute("message") String message) {
        Iterable<Book> books = bookService.findAll();

        model.addAttribute("pageName", pageName);
        model.addAttribute("books", books);

        return "book/findAll";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(HttpServletRequest request, @PathVariable long id) throws ResourceNotFoundException {
        Optional<Book> bookOptional = bookService.findById(id);

        if (!bookOptional.isPresent()) {
            throw  new ResourceNotFoundException("Resource with ID " + id + " was not found.");
        }

        return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/create")
    public String create(Book book, Model model) {
        Iterable<Category> categories = categoryService.findAll();

        model.addAttribute("pageName", pageName + " - Create");
        model.addAttribute("categories", categories);

        return "book/create";
    }

    @PostMapping("/create")
    public String create(@Valid Book book, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("pageName", pageName);

        if (result.hasErrors()) {
            Iterable<Category> categories = categoryService.findAll();

            model.addAttribute("pageName", pageName + " - Create");
            model.addAttribute("categories", categories);

            return "book/create";
        }

        bookService.save(book);

        redirectAttributes.addFlashAttribute("message", "Book has been saved!");

        return "redirect:/books";
    }
}
