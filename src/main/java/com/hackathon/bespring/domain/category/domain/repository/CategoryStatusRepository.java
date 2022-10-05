package com.hackathon.bespring.domain.category.domain.repository;

import com.hackathon.bespring.domain.category.domain.CategoryStatus;
import com.hackathon.bespring.domain.category.domain.CategoryStatusId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryStatusRepository extends JpaRepository<CategoryStatus, CategoryStatusId> {
}
