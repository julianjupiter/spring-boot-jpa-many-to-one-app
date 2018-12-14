package io.github.julianjupiter.springbootjpamanytoone.service;

import io.github.julianjupiter.springbootjpamanytoone.domain.Book;
import io.github.julianjupiter.springbootjpamanytoone.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Iterable<Book> findByCategoryName(String category) {
        return bookRepository.findByCategoryName(category);
    }

    @Override
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void delete(long id) {
        bookRepository.deleteById(id);
    }
}
