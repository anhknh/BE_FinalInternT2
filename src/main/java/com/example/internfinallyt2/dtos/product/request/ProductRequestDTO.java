package com.example.internfinallyt2.dtos.product.request;

import com.example.internfinallyt2.dtos.BaseEntityDTO;
import com.example.internfinallyt2.exception.customValidation.customAnnotation.ImageFile;
import com.example.internfinallyt2.exception.customValidation.customAnnotation.PositiveInteger;
import com.example.internfinallyt2.exception.customValidation.customAnnotation.PositiveLongArray;
import com.example.internfinallyt2.exception.customValidation.customAnnotation.Status;
import com.example.internfinallyt2.exception.groupValidation.CreateGroup;
import com.example.internfinallyt2.exception.groupValidation.UpdateGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO extends BaseEntityDTO {
    private Long id;
    @NotBlank(message = "NotBlank", groups = {CreateGroup.class, UpdateGroup.class})
    @Length(min = 1, max = 255, message = "Length", groups = {CreateGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Pattern", groups = {CreateGroup.class, UpdateGroup.class})
    private String name;
    @NotBlank(message = "NotBlank", groups = {CreateGroup.class})
    @Length(min = 6, max = 8, message = "Length", groups = {CreateGroup.class})
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Pattern", groups = {CreateGroup.class})
    private String productCode;
    @NotBlank(message = "NotBlank", groups = {CreateGroup.class, UpdateGroup.class})
    @Length(min = 1, max = 255, message = "Length", groups = {CreateGroup.class, UpdateGroup.class})
    private String description;
    @NotBlank(message = "NotBlank", groups = {CreateGroup.class, UpdateGroup.class})
    @PositiveInteger(message = "PositiveInteger", groups = {CreateGroup.class, UpdateGroup.class})
    private String price;
    @NotBlank(message = "NotBlank", groups = {CreateGroup.class, UpdateGroup.class})
    @PositiveInteger(message = "PositiveInteger", groups = {CreateGroup.class, UpdateGroup.class})
    private String quantity;
    @Status(message = "Status", groups = {UpdateGroup.class})
    private Integer status;
    @PositiveLongArray(message = "PositiveLongArray", groups = {CreateGroup.class, UpdateGroup.class})
    private Long[] categoryIds;
    @ImageFile(message = "ImageFile", groups = {CreateGroup.class, UpdateGroup.class})
    private MultipartFile image;
}
