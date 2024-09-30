package com.example.internfinallyt2.mapper.category.response;


import com.example.internfinallyt2.dtos.ProductCategoryDTO;
import com.example.internfinallyt2.dtos.category.response.CategoryResponseDTO;
import com.example.internfinallyt2.dtos.product.response.ProductSearchResponseDTO;
import com.example.internfinallyt2.entity.Category;
import com.example.internfinallyt2.entity.Product;
import com.example.internfinallyt2.utils.VariablePath;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {
    CategoryResponseDTO toCategoryDTO(Category category);
    Category toCategory(CategoryResponseDTO categoryResponseDTO);
    List<CategoryResponseDTO> toListCategoryDTO(List<Category> categoryList);
    List<Category> toListCategory(List<CategoryResponseDTO> categoryResponseDTOList);

    default Page<CategoryResponseDTO> ToCategoryResponseDTOPage(Page<Category> categoryPage) {
        return categoryPage.map(this::toCategoryDTO);
    }

    @AfterMapping
    default void afterMapCategoryDTO(@MappingTarget CategoryResponseDTO categoryResponseDTO) {
        if(categoryResponseDTO.getUrlImage() != null ) {
            categoryResponseDTO.setUrlImage(VariablePath.urlImage + categoryResponseDTO.getUrlImage());
        }
    }
}
