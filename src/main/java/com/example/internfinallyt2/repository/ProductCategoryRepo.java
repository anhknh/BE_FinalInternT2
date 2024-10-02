package com.example.internfinallyt2.repository;

import com.example.internfinallyt2.entity.ProductCategory;
import com.example.internfinallyt2.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> {
    boolean existsByCategory_CategoryCodeAndStatusAndProduct_Status(String categoryCode, Status statusCate, Status statusProduct);
}
