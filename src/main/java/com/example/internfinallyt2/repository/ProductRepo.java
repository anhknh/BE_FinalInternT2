package com.example.internfinallyt2.repository;

import com.example.internfinallyt2.entity.Category;
import com.example.internfinallyt2.entity.Product;
import com.example.internfinallyt2.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "select p from Product p left join fetch p.productCategories pc left join fetch pc.category")
    public List<Product> findAll();

    @Query(value = "select p from Product  p left join fetch p.productCategories pc" +
            " left join fetch pc.category " +
            "where p.id = :id AND pc.status = 1")
    public Optional<Product> findById1(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Product p" +
            " LEFT JOIN FETCH p.productCategories pc" +
            " LEFT JOIN FETCH pc.category c" +
            " WHERE p.status = 1 AND pc.status = 1" +
            " AND (:name IS NULL OR LOWER(p.name) LIKE LOWER( :name))" +
            " AND (:productCode IS NULL OR LOWER(p.productCode) LIKE LOWER( :productCode))" +
            " AND (:startDate IS NULL OR p.createdDate >= :startDate)" +
            " AND (:endDate IS NULL OR p.createdDate <= :endDate)" +
            " AND (:categoryCode IS NULL OR EXISTS (" +
            " SELECT 1 FROM ProductCategory pc2 JOIN pc2.category c2" +
            " WHERE pc2.product = p AND pc2.status=1 AND c2.categoryCode LIKE LOWER( :categoryCode)" +
            "))")
    Page<Product> searchProducts(
            @Param("name") String name,
            @Param("productCode") String productCode,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("categoryCode") String categoryCode,
            Pageable pageable);
    boolean existsByproductCodeAndStatus(String productCode, Status status);



}
