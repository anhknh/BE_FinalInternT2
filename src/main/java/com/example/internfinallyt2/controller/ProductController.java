package com.example.internfinallyt2.controller;


import com.example.internfinallyt2.dtos.product.request.ProductRequestDTO;
import com.example.internfinallyt2.dtos.product.response.ProductResponseDTO;
import com.example.internfinallyt2.dtos.product.response.ProductSearchResponseDTO;
import com.example.internfinallyt2.exception.groupValidation.CreateGroup;
import com.example.internfinallyt2.exception.groupValidation.UpdateGroup;
import com.example.internfinallyt2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/view-all-product")
    public ResponseEntity<List<ProductResponseDTO>> viewAllProduct() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/view-detail-product")
    public ResponseEntity<ProductSearchResponseDTO> viewDetail(@RequestParam(value = "id", required = false) Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/search-product")
    public ResponseEntity<Page<ProductSearchResponseDTO>> searchProduct(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "productCode", required = false) String productCode,
                                                       @RequestParam(value = "categoryCode", required = false) String categoryCode,
                                                       @RequestParam(value = "startDate", required = false)
                                                           @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                       @RequestParam(value = "endDate", required = false)
                                                           @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                                                       @RequestParam(value = "size", required = false) Optional<Integer> size,
                                                       @RequestParam(value = "page", required = false) Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5));
        return ResponseEntity.ok(productService.searchProducts(name, productCode, startDate, endDate, categoryCode, pageable));
    }

    @GetMapping("/export-excel-product")
    public ResponseEntity<byte[]> exportExcel(@RequestParam(value = "fileName", required = false) String fileName,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "productCode", required = false) String productCode,
                                              @RequestParam(value = "categoryCode", required = false) String categoryCode,
                                              @RequestParam(value = "startDate", required = false)
                                                  @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                              @RequestParam(value = "endDate", required = false)
                                                  @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        return productService.exportExcel(name,productCode,startDate,endDate,categoryCode,fileName);
    }


    @PostMapping("/create-product")
    public ResponseEntity<ProductResponseDTO> createProduct(@Validated(CreateGroup.class) @ModelAttribute ProductRequestDTO productRequestDTO) {
        return ResponseEntity.ok(productService.createProduct(productRequestDTO));
    }

    @PutMapping("/update-product")
    public ResponseEntity<ProductResponseDTO> updateProduct(@Validated(UpdateGroup.class) @ModelAttribute ProductRequestDTO productRequestDTO) {
        return ResponseEntity.ok(productService.updateProduct(productRequestDTO));
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<ProductResponseDTO> deleteProduct(@RequestParam(value = "id", required = false) Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
