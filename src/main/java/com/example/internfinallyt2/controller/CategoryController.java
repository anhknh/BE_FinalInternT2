package com.example.internfinallyt2.controller;

import com.example.internfinallyt2.dtos.category.request.CategoryRequestDTO;
import com.example.internfinallyt2.dtos.category.response.CategoryResponseDTO;
import com.example.internfinallyt2.exception.groupValidation.CreateGroup;
import com.example.internfinallyt2.exception.groupValidation.UpdateGroup;
import com.example.internfinallyt2.service.CategoryService;
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
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/view-all-category")
    public ResponseEntity<List<CategoryResponseDTO>> viewAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/view-detail")
    public ResponseEntity<CategoryResponseDTO> viewDetail(@RequestParam(value = "id", required = false) Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/search-category")
    public ResponseEntity<Page<CategoryResponseDTO>> searchCategory(@RequestParam(value = "name", required = false) String name,
                                                                    @RequestParam(value = "categoryCode", required = false) String categoryCode,
                                                                    @RequestParam(value = "startDate", required = false)
                                                                        @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                                    @RequestParam(value = "endDate", required = false)
                                                                        @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
                                                                    @RequestParam(value = "size", required = false) Optional<Integer> size,
                                                                    @RequestParam(value = "page", required = false) Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), size.orElse(5));
        return ResponseEntity.ok(categoryService.searchCategory(name, categoryCode, startDate, endDate, pageable));
    }

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportExcel(@RequestParam(value = "fileName", required = false) String fileName,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "categoryCode", required = false) String categoryCode,
                                              @RequestParam(value = "startDate", required = false)
                                                  @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                              @RequestParam(value = "endDate", required = false)
                                                  @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        return categoryService.exportExcel(fileName, name, categoryCode, startDate, endDate);
    }

    @PostMapping("/create-category")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Validated(CreateGroup.class) @ModelAttribute CategoryRequestDTO categoryRequestDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryRequestDTO));
    }

    @PutMapping("/update-category")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@Validated(UpdateGroup.class) @ModelAttribute CategoryRequestDTO categoryRequestDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryRequestDTO));
    }

    @DeleteMapping("/delete-category")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@RequestParam(value = "id", required = false) Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
