package io.github.julianjupiter.springbootjpamanytoone.controller;

import io.github.julianjupiter.springbootjpamanytoone.domain.Book;
import io.github.julianjupiter.springbootjpamanytoone.domain.Category;
import io.github.julianjupiter.springbootjpamanytoone.exception.CustomApiExceptionHandler;
import io.github.julianjupiter.springbootjpamanytoone.exception.ResourceNotFoundException;
import io.github.julianjupiter.springbootjpamanytoone.form.BookForm;
import io.github.julianjupiter.springbootjpamanytoone.service.BookService;
import io.github.julianjupiter.springbootjpamanytoone.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "/{id}")
    public ResponseEntity<Book> update(@PathVariable long id, @RequestBody BookForm bookForm) throws ResourceNotFoundException {
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            throw  new ResourceNotFoundException("Resource with ID " + id + " was not found.");
        }

        Book updatedBook = new Book();
        updatedBook.setId(id);
        updatedBook.setTitle(bookForm.getTitle());
        updatedBook.setEdition(bookForm.getEdition());
        updatedBook.setAuthor(bookForm.getAuthor());
        updatedBook.setDescription(bookForm.getDescription());
        Optional<Category> categoryOptional = categoryService.findById(bookForm.getCategoryId());
        updatedBook.setCategory(categoryOptional.orElseGet(() -> updatedBook.getCategory()));
        bookService.save(updatedBook);

        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
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
