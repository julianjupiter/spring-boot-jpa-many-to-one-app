package io.github.julianjupiter.springbootjpamanytoone.repository;

import io.github.julianjupiter.springbootjpamanytoone.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
