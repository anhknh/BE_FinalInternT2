package com.example.internfinallyt2.mapper.product.response;

import com.example.internfinallyt2.dtos.ProductCategoryDTO;
import com.example.internfinallyt2.dtos.category.response.CategoryResponseDTO;
import com.example.internfinallyt2.dtos.product.response.ProductSearchResponseDTO;
import com.example.internfinallyt2.entity.Category;
import com.example.internfinallyt2.entity.Product;
import com.example.internfinallyt2.mapper.ProductCategoryMapper;
import com.example.internfinallyt2.mapper.category.response.CategoryResponseMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryResponseMapper.class, ProductCategoryMapper.class})
public interface ProductSearchResponseMapper {

    @Mapping(source = "productCategories", target = "productCategoryDTOS")
    ProductSearchResponseDTO ToProductSearchResponseDTO(Product product);
    @Mapping(source = "productCategoryDTOS", target = "productCategories")
    Product ToProduct(ProductSearchResponseDTO productSearchResponseDTO);
    List<ProductSearchResponseDTO> ToProductSearchResponseDTOList(List<Product> productList);
    List<Product> ToProductList(List<ProductSearchResponseDTO> productSearchResponseDTOList);

    default Page<ProductSearchResponseDTO> ToProductSearchResponseDTOPage(Page<Product> productPage) {
        return productPage.map(this::ToProductSearchResponseDTO);
    }

    @AfterMapping
    default void afterMapDTO(@MappingTarget ProductSearchResponseDTO productSearchResponseDTO) {
        List<CategoryResponseDTO> list = productSearchResponseDTO.getProductCategoryDTOS().stream().map(ProductCategoryDTO::getCategory).toList();
        List<String> listCode = list.stream().map(CategoryResponseDTO::getCategoryCode).toList();
        productSearchResponseDTO.setCategory(String.join(", ", listCode));
        List<Long> listId = list.stream().map(CategoryResponseDTO::getId).toList();
        productSearchResponseDTO.setCategory(String.join(", ", listCode));
        productSearchResponseDTO.setCategoryId(listId.toString());
    }
}
