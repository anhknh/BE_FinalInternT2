package com.example.internfinallyt2.dtos.product.response;

import com.example.internfinallyt2.dtos.BaseEntityDTO;
import com.example.internfinallyt2.dtos.ProductCategoryDTO;
import com.example.internfinallyt2.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResponseDTO extends BaseEntityDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String productCode;
    private Long quantity;
    private String urlImage;
    private Status status;
    private String category;
    private String categoryId;
    @JsonIgnore
    private List<ProductCategoryDTO> productCategoryDTOS;

    public ProductSearchResponseDTO(Date createdDate, Date modifiedDate, String createdBy, String modifiedBy, Long id, String name, String description, Double price, String productCode, Long quantity, String urlImage, Status status, String category, List<ProductCategoryDTO> productCategoryDTOS) {
        super(createdDate, modifiedDate, createdBy, modifiedBy);
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productCode = productCode;
        this.quantity = quantity;
        this.urlImage = urlImage;
        this.status = status;
        this.category = category;
        this.productCategoryDTOS = productCategoryDTOS;
    }
}
