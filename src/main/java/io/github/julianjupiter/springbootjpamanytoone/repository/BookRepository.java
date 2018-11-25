package io.github.julianjupiter.springbootjpamanytoone.repository;

import io.github.julianjupiter.springbootjpamanytoone.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
