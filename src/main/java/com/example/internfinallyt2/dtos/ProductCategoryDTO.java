package com.example.internfinallyt2.dtos;

import com.example.internfinallyt2.dtos.category.response.CategoryResponseDTO;
import com.example.internfinallyt2.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDTO extends BaseEntityDTO {
    private Long id;
    private CategoryResponseDTO category;
    private Status status;
}
