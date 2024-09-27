package com.example.internfinallyt2.repository;

import com.example.internfinallyt2.dtos.category.response.CategoryResponseDTO;
import com.example.internfinallyt2.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;


public interface CategoryRepo extends JpaRepository<Category, Long> {

    @Query("SELECT new com.example.internfinallyt2.dtos.category.response.CategoryResponseDTO(" +
            "c.id, c.categoryCode, c.name, c.description, c.urlImage, c.status, " +
            "c.createdDate, c.modifiedDate, c.createdBy, c.modifiedBy) " +
            "FROM Category c " +
            "WHERE (:name IS NULL OR c.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:categoryCode IS NULL OR c.categoryCode LIKE CONCAT('%', :categoryCode, '%')) " +
            "AND (:startDate IS NULL OR c.createdDate >= :startDate) " +
            "AND (c.status = 1)" +
            "AND (:endDate IS NULL OR c.createdDate <= :endDate)")
    Page<CategoryResponseDTO> searchCategories(
            @Param("name") String name,
            @Param("categoryCode") String categoryCode,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            Pageable pageable);

    boolean existsByCategoryCode(String categoryCode);
}
