package io.github.julianjupiter.springbootjpamanytoone.repository;

import io.github.julianjupiter.springbootjpamanytoone.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
