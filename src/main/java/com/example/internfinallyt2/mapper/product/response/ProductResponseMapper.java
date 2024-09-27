package com.example.internfinallyt2.mapper.product.response;

import com.example.internfinallyt2.dtos.product.response.ProductResponseDTO;
import com.example.internfinallyt2.entity.Product;
import com.example.internfinallyt2.mapper.ProductCategoryMapper;
import com.example.internfinallyt2.mapper.category.response.CategoryResponseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryResponseMapper.class, ProductCategoryMapper.class})
public interface ProductResponseMapper {

    @Mapping(source = "productCategories", target = "productCategoryDTOS")
    ProductResponseDTO toProductDTO(Product product);
    @Mapping(source = "productCategoryDTOS", target = "productCategories")
    Product toProduct(ProductResponseDTO productResponseDTO);
    List<ProductResponseDTO> toListProductDTO(List<Product> products);
    List<Product> toListProducts(List<ProductResponseDTO> productResponseDTOS);
}
