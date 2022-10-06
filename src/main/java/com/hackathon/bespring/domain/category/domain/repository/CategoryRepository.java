package com.hackathon.bespring.domain.category.domain.repository;

import com.hackathon.bespring.domain.category.domain.Categories;
import com.hackathon.bespring.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategory(Categories categories);
}
