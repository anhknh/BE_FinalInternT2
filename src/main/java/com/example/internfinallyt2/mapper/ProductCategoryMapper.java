package com.example.internfinallyt2.mapper;


import com.example.internfinallyt2.dtos.ProductCategoryDTO;
import com.example.internfinallyt2.entity.ProductCategory;
import com.example.internfinallyt2.mapper.category.response.CategoryResponseMapper;
import com.example.internfinallyt2.mapper.product.response.ProductResponseMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductResponseMapper.class, CategoryResponseMapper.class})
public interface ProductCategoryMapper {
    ProductCategoryDTO toProductCategoryDTO(ProductCategory productCategory);
    ProductCategory toProductCategory(ProductCategoryDTO productCategoryDTO);
    List<ProductCategoryDTO> toListProductCategoryDTO(List<ProductCategory> productCategoryList);
    List<ProductCategory> toListProductCategory(List<ProductCategoryDTO> productCategoryDTOList);
}
