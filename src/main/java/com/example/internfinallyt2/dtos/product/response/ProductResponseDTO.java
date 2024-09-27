package com.example.internfinallyt2.dtos.product.response;

import com.example.internfinallyt2.dtos.BaseEntityDTO;
import com.example.internfinallyt2.dtos.ProductCategoryDTO;
import com.example.internfinallyt2.enums.Status;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO extends BaseEntityDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String productCode;
    private Long quantity;
    private String urlImage;
    private Status status;
    private List<ProductCategoryDTO> productCategoryDTOS;
}
