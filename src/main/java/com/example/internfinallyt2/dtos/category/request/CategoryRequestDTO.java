package com.example.internfinallyt2.dtos.category.request;

import com.example.internfinallyt2.dtos.BaseEntityDTO;
import com.example.internfinallyt2.exception.customValidation.customAnnotation.ImageFile;
import com.example.internfinallyt2.exception.customValidation.customAnnotation.Status;
import com.example.internfinallyt2.exception.groupValidation.CreateGroup;
import com.example.internfinallyt2.exception.groupValidation.UpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryRequestDTO extends BaseEntityDTO {

    private Long id;
    @NotBlank(message = "NotBlank", groups = {CreateGroup.class, UpdateGroup.class})
    @Length(min = 1, max = 255, message = "Length", groups = {CreateGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Pattern", groups = {CreateGroup.class, UpdateGroup.class})
    private String name;
    @NotBlank(message = "NotBlank", groups = {CreateGroup.class})
    @Length(min = 6, max = 8, message = "Length", groups = {CreateGroup.class})
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Pattern", groups = {CreateGroup.class})
    private String categoryCode;
    @NotBlank(message = "NotBlank", groups = {CreateGroup.class, UpdateGroup.class})
    @Length(min = 1, max = 255, message = "Length", groups = {CreateGroup.class, UpdateGroup.class})
    private String description;
    @Status(message = "Status", groups = {UpdateGroup.class})
    private Integer status;
    @ImageFile(message = "ImageFile", groups = {CreateGroup.class, UpdateGroup.class})
    private MultipartFile image;

}
