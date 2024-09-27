package com.example.internfinallyt2.entity;


import com.example.internfinallyt2.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productCode;
    private String name;
    private double price;
    private int quantity;
    private String description;
    private String urlImage;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Status status;
    @OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<ProductCategory> productCategories;

}
