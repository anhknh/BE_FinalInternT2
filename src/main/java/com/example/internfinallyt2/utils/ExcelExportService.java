package com.example.internfinallyt2.utils;

import com.example.internfinallyt2.dtos.category.response.CategoryResponseDTO;
import com.example.internfinallyt2.dtos.product.response.ProductSearchResponseDTO;
import com.example.internfinallyt2.exception.customValidation.CustomIOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    public ResponseEntity<byte[]> exportCategoriesToExcel(List<CategoryResponseDTO> categoryList, String fileName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Categories");

        // Tạo hàng tiêu đề
        String[] columns = {"ID", "Category Code", "Name", "Description", "Image URL", "Status", "Created Date", "Modified Date", "Created By", "Modified By"};
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Điền dữ liệu từ danh sách CategoryResponseDTO
        int rowNum = 1;
        for (CategoryResponseDTO category : categoryList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(category.getId());
            row.createCell(1).setCellValue(category.getCategoryCode());
            row.createCell(2).setCellValue(category.getName());
            row.createCell(3).setCellValue(category.getDescription());
            row.createCell(4).setCellValue(category.getUrlImage());
            row.createCell(5).setCellValue(category.getStatus().toString());
            row.createCell(6).setCellValue(category.getCreatedDate() != null ?category.getCreatedDate().toString() :"");
            row.createCell(7).setCellValue(category.getModifiedDate() != null ?category.getModifiedDate().toString() :"");
            row.createCell(8).setCellValue(category.getCreatedBy());
            row.createCell(9).setCellValue(category.getModifiedBy());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi dữ liệu vào byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
            workbook.close();
        }catch (IOException e) {
            throw new CustomIOException(e);
        }
        if (fileName == null || fileName.isBlank()) {
            fileName = "categories.xls";
        }
        // Tạo response với header để download file Excel
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(out.toByteArray());
    }

    public ResponseEntity<byte[]> exportProductsToExcel(List<ProductSearchResponseDTO> productList, String fileName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        // Tạo hàng tiêu đề
        String[] columns = {"ID", "Product Code", "Name", "Description", "Price", "Quantity", "Image URL", "Status", "Category"
                , "Created Date", "Modified Date", "Created By", "Modified By"};
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Điền dữ liệu từ danh sách ProductResponseDTO
        int rowNum = 1;
        for (ProductSearchResponseDTO product : productList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getProductCode());
            row.createCell(2).setCellValue(product.getName());
            row.createCell(3).setCellValue(product.getDescription());
            row.createCell(4).setCellValue(product.getPrice());
            row.createCell(5).setCellValue(product.getQuantity());
            row.createCell(6).setCellValue(product.getUrlImage());
            row.createCell(7).setCellValue(product.getStatus().toString());
            row.createCell(8).setCellValue(product.getCategory());
            row.createCell(9).setCellValue(product.getCreatedDate() != null ? product.getCreatedDate().toString() :"");
            row.createCell(10).setCellValue(product.getModifiedDate() != null ? product.getModifiedDate().toString() :"");
            row.createCell(11).setCellValue(product.getCreatedBy());
            row.createCell(12).setCellValue(product.getModifiedBy());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi dữ liệu vào byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            throw new CustomIOException(e);
        }
        if (fileName == null || fileName.isBlank()) {
            fileName = "products.xls";
        }
        // Tạo response với header để download file Excel
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(out.toByteArray());
    }

}
