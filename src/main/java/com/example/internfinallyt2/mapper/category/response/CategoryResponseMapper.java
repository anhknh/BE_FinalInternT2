package com.example.internfinallyt2.mapper.category.response;


import com.example.internfinallyt2.dtos.category.response.CategoryResponseDTO;
import com.example.internfinallyt2.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {
    CategoryResponseDTO toCategoryDTO(Category category);
    Category toCategory(CategoryResponseDTO categoryResponseDTO);
    List<CategoryResponseDTO> toListCategoryDTO(List<Category> categoryList);
    List<Category> toListCategory(List<CategoryResponseDTO> categoryResponseDTOList);
}
