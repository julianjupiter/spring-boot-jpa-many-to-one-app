package io.github.julianjupiter.springbootjpamanytoone.service;

import io.github.julianjupiter.springbootjpamanytoone.domain.Category;

import java.util.Optional;

public interface CategoryService {
    Iterable<Category> findAll();
    Optional<Category> findById(long id);
    void save(Category category);
    void deleteById(long id);
}
