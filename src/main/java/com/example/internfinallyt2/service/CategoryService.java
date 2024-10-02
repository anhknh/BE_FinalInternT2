package com.example.internfinallyt2.service;

import com.example.internfinallyt2.dtos.category.request.CategoryRequestDTO;
import com.example.internfinallyt2.dtos.category.response.CategoryResponseDTO;
import com.example.internfinallyt2.entity.Category;
import com.example.internfinallyt2.enums.Status;
import com.example.internfinallyt2.exception.customValidation.CategoryInUseException;
import com.example.internfinallyt2.exception.customValidation.DuplicateCourseCodeException;
import com.example.internfinallyt2.exception.customValidation.ResourceNotFoundException;
import com.example.internfinallyt2.mapper.category.response.CategoryResponseMapper;
import com.example.internfinallyt2.repository.CategoryRepo;
import com.example.internfinallyt2.repository.ProductCategoryRepo;
import com.example.internfinallyt2.utils.ExcelExportService;
import com.example.internfinallyt2.utils.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryResponseMapper categoryMapper;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ProductCategoryRepo productCategoryRepo;
    @Autowired
    FileUpload fileUpload;
    @Autowired
    ExcelExportService excelExportService;

    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepo.findByStatusOrderByCategoryCode(Status.ACTIVE);
        return categoryMapper.toListCategoryDTO(categories);
    }

    public Page<CategoryResponseDTO> searchCategory(String name, String codeCategory,
                                                    Date startDate, Date endDate, Pageable pageable) {
        if(codeCategory != null) {
            codeCategory = "%" + codeCategory + "%";
        }
        if(name != null) {
            name = "%" + name + "%";
        }
        Page<Category> result = categoryRepo.searchCategories(name,codeCategory,startDate,endDate, pageable);
        return categoryMapper.ToCategoryResponseDTOPage(result);
    }

    public ResponseEntity<byte[]> exportExcel (String fileName, String name, String codeCategory,
                                       Date startDate, Date endDate) {
        if(codeCategory != null) {
            codeCategory = "%" + codeCategory + "%";
        }
        if(name != null) {
            name = "%" + name + "%";
        }
            return excelExportService.exportCategoriesToExcel(searchCategory(
                    name, codeCategory, startDate, endDate, null).getContent(),
                    fileName);
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        if (!(id != null && categoryRepo.existsById(id))) {
            throw new ResourceNotFoundException("Category", id);
        }
        Category category = categoryRepo.findById(id).orElse(null);
        if (category != null) {
            return categoryMapper.toCategoryDTO(category);
        }
        throw new ResourceNotFoundException("Category", id);
    }

    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        if(categoryRepo.existsByCategoryCodeAndStatus(categoryRequestDTO.getCategoryCode(), Status.ACTIVE)) {
            throw new DuplicateCourseCodeException("CategoryCode", categoryRequestDTO.getCategoryCode());
        }
        Category category = new Category();
        category.setCategoryCode(categoryRequestDTO.getCategoryCode());
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());
        category.setUrlImage(fileUpload.saveFile(categoryRequestDTO.getImage(), null));
        category.setStatus(Status.ACTIVE);
        return categoryMapper.toCategoryDTO(categoryRepo.save(category));
    }

    @Transactional
    public CategoryResponseDTO updateCategory(CategoryRequestDTO categoryRequestDTO) {
        if (!(categoryRequestDTO.getId() != null && categoryRepo.existsById(categoryRequestDTO.getId()))) {
            throw new ResourceNotFoundException("Category", categoryRequestDTO.getId());
        }
        Category category = categoryRepo.findById(categoryRequestDTO.getId()).orElse(null);
        if (category != null && category.getStatus() == Status.ACTIVE) {
            category.setName(categoryRequestDTO.getName());
            category.setUrlImage(fileUpload.saveFile(categoryRequestDTO.getImage(), category.getUrlImage()));
            category.setStatus(categoryRequestDTO.getStatus() == 1 ? Status.ACTIVE : Status.INACTIVE);
            category.setDescription(categoryRequestDTO.getDescription());
            return categoryMapper.toCategoryDTO(categoryRepo.save(category));
        }
        throw new ResourceNotFoundException("Category", categoryRequestDTO.getId());
    }

    @Transactional
    public CategoryResponseDTO deleteCategory(Long id) {
        if (!(id != null && categoryRepo.existsById(id))) {
            throw new ResourceNotFoundException("Category", id);
        }
        Category category = categoryRepo.findById(id).orElse(null);
        if (category != null && category.getStatus() == Status.ACTIVE) {
            if (productCategoryRepo.existsByCategory_CategoryCodeAndStatusAndProduct_Status(category.getCategoryCode(),
                    Status.ACTIVE, Status.ACTIVE)) {
                throw new CategoryInUseException(category.getName(), category.getCategoryCode());
            }
            category.setStatus(Status.INACTIVE);
            return categoryMapper.toCategoryDTO(categoryRepo.save(category));
        }
        throw new ResourceNotFoundException("Category", id);
    }
}
