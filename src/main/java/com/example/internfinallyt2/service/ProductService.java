package com.example.internfinallyt2.service;

import com.example.internfinallyt2.dtos.product.request.ProductRequestDTO;
import com.example.internfinallyt2.dtos.product.response.ProductResponseDTO;
import com.example.internfinallyt2.dtos.product.response.ProductSearchResponseDTO;
import com.example.internfinallyt2.entity.Category;
import com.example.internfinallyt2.entity.Product;
import com.example.internfinallyt2.entity.ProductCategory;
import com.example.internfinallyt2.enums.Status;
import com.example.internfinallyt2.exception.customValidation.DuplicateCourseCodeException;
import com.example.internfinallyt2.exception.customValidation.ResourceNotFoundException;
import com.example.internfinallyt2.mapper.product.response.ProductResponseMapper;
import com.example.internfinallyt2.mapper.product.response.ProductSearchResponseMapper;
import com.example.internfinallyt2.repository.CategoryRepo;
import com.example.internfinallyt2.repository.ProductCategoryRepo;
import com.example.internfinallyt2.repository.ProductRepo;
import com.example.internfinallyt2.utils.ExcelExportService;
import com.example.internfinallyt2.utils.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ProductCategoryRepo productCategoryRepo;
    @Autowired
    ProductResponseMapper productResponseMapper;
    @Autowired
    ProductSearchResponseMapper productSearchResponseMapper;
    @Autowired
    FileUpload fileUpload;
    @Autowired
    ExcelExportService excelExportService;

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return productResponseMapper.toListProductDTO(products);
    }

    public Page<ProductSearchResponseDTO> searchProducts(String name, String productCode,
                                                         Date startDate, Date endDate,
                                                         String categoryCode, Pageable pageable) {
        if(categoryCode != null) {
            categoryCode = "%" + categoryCode + "%";
        }
        if(name != null) {
            name = "%" + name + "%";
        }
        if (productCode != null) {
            productCode = "%" + productCode + "%";
        }
        Page<Product> result = productRepo.searchProducts(name, productCode, startDate, endDate, categoryCode, pageable);
        return productSearchResponseMapper.ToProductSearchResponseDTOPage(result);
    }

    public ResponseEntity<byte[]> exportExcel (String name, String productCode,
                                               Date startDate, Date endDate,
                                               String categoryCode, String fileName) {
        if(categoryCode != null) {
            categoryCode = "%" + categoryCode + "%";
        }
        if(name != null) {
            name = "%" + name + "%";
        }
        if (productCode != null) {
            productCode = "%" + productCode + "%";
        }
        return excelExportService.exportProductsToExcel(searchProducts(
                        name, productCode, startDate, endDate, categoryCode,null).getContent(),
                fileName);
    }

    public ProductSearchResponseDTO getProductById(Long id) {
        if (!(id != null && productRepo.existsById(id))) {
            throw new ResourceNotFoundException("Product", id);
        }
        Product product = productRepo.findById1(id).orElse(null);
        if (product != null) {
            return productSearchResponseMapper.ToProductSearchResponseDTO(product);
        }
        throw new ResourceNotFoundException("Product", id);
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        if(productRepo.existsByproductCode(productRequestDTO.getProductCode())) {
            throw new DuplicateCourseCodeException("Product", productRequestDTO.getProductCode());
        }
        List<Category> selectedCategory = categoryRepo.findAllById(Arrays.asList(productRequestDTO.getCategoryIds()));
        if (selectedCategory.size() <= 0) {
            throw new ResourceNotFoundException("Category", Arrays.asList(productRequestDTO.getCategoryIds()));
        }
        Product product = new Product();
        product.setProductCode(productRequestDTO.getProductCode());
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(Double.parseDouble(productRequestDTO.getPrice()));
        product.setQuantity(Integer.parseInt(productRequestDTO.getQuantity()));
        product.setStatus(Status.ACTIVE);
        product.setUrlImage(fileUpload.saveFile(productRequestDTO.getImage(), null));
        Product savedProduct = productRepo.save(product);
        for (Category category : selectedCategory) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setCategory(category);
            productCategory.setProduct(savedProduct);
            productCategory.setStatus(Status.ACTIVE);
            productCategoryRepo.save(productCategory);
        }
        return productResponseMapper.toProductDTO(savedProduct);
    }

    @Transactional
    public ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO) {
        if (!(productRequestDTO.getId() != null && productRepo.existsById(productRequestDTO.getId()))) {
            throw new ResourceNotFoundException("Product", productRequestDTO.getId());
        }
        Product product = productRepo.findById(productRequestDTO.getId()).orElse(null);
        List<Category> selectedCategory = categoryRepo.findAllById(Arrays.asList(productRequestDTO.getCategoryIds()));
        if (selectedCategory.size() <= 0) {
            throw new ResourceNotFoundException("Category", Arrays.asList(productRequestDTO.getCategoryIds()));
        }
        if (product != null && product.getStatus() == Status.ACTIVE) {
            List<ProductCategory> productCategoryList = product.getProductCategories();
            List<Long> selectedCategoryIds = selectedCategory.stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            product.setName(productRequestDTO.getName());
            product.setDescription(productRequestDTO.getDescription());
            product.setPrice(Double.parseDouble(productRequestDTO.getPrice()));
            product.setQuantity(Integer.parseInt(productRequestDTO.getQuantity()));
            product.setStatus(productRequestDTO.getStatus() == 1 ? Status.ACTIVE : Status.INACTIVE);
            product.setUrlImage(fileUpload.saveFile(productRequestDTO.getImage(), product.getUrlImage()));
            for (ProductCategory productCategory : productCategoryList) {
                if (!selectedCategoryIds.contains(productCategory.getCategory().getId())) {
                    productCategory.setStatus(Status.INACTIVE);
                    productCategoryRepo.save(productCategory);
                } else {
                    if(productCategory.getStatus() == Status.INACTIVE) {
                        productCategory.setStatus(Status.ACTIVE);
                        productCategoryRepo.save(productCategory);
                    }
                    selectedCategoryIds.remove(productCategory.getCategory().getId());
                }
            }
            List<Category> categoryNew = categoryRepo.findAllById(selectedCategoryIds);
            if(categoryNew.size() > 0) {
                for (Category category : categoryNew) {
                    ProductCategory newProductCategory = new ProductCategory();
                    newProductCategory.setProduct(product);
                    newProductCategory.setCategory(category);
                    newProductCategory.setStatus(Status.ACTIVE);
                    productCategoryRepo.save(newProductCategory);
                }
            }
            return productResponseMapper.toProductDTO(productRepo.save(product));
        }
        throw new ResourceNotFoundException("Product", productRequestDTO.getId());
    }

    @Transactional
    public ProductResponseDTO deleteProduct(Long id) {
        if (!(id != null && productRepo.existsById(id))) {
            throw new ResourceNotFoundException("Product", id);
        }
        Product product = productRepo.findById(id).orElse(null);
        if (product != null && product.getStatus() == Status.ACTIVE) {
            product.setStatus(Status.INACTIVE);
            return productResponseMapper.toProductDTO(product);
        }
        throw new ResourceNotFoundException("Product", id);
    }
}
