package io.github.julianjupiter.springbootjpamanytoone.service;

import io.github.julianjupiter.springbootjpamanytoone.domain.Book;

import java.util.Optional;

public interface BookService {
    Iterable<Book> findAll();
    Optional<Book> findById(long id);
    void save(Book book);
    void delete(long id);
}
