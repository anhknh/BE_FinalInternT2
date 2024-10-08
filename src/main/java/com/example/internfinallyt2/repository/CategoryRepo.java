package com.example.internfinallyt2.repository;

import com.example.internfinallyt2.entity.Category;
import com.example.internfinallyt2.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface CategoryRepo extends JpaRepository<Category, Long> {

    List<Category> findByStatusOrderByCategoryCode(Status status);

    @Query("SELECT c " +
            "FROM Category c " +
            "WHERE (:name IS NULL OR c.name LIKE :name) " +
            "AND (:categoryCode IS NULL OR c.categoryCode LIKE :categoryCode) " +
            "AND (:startDate IS NULL OR c.createdDate >= :startDate) " +
            "AND (c.status = 1)" +
            "AND (:endDate IS NULL OR c.createdDate <= :endDate)")
    Page<Category> searchCategories(
            @Param("name") String name,
            @Param("categoryCode") String categoryCode,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            Pageable pageable);

    boolean existsByCategoryCodeAndStatus(String categoryCode, Status status);

    List<Category> findAllByIdInAndStatus(List<Long> ids, Status status);
}
