package com.example.internfinallyt2.dtos.category.response;

import com.example.internfinallyt2.dtos.BaseEntityDTO;
import com.example.internfinallyt2.enums.Status;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO extends BaseEntityDTO {
    private Long id;
    private String categoryCode;
    private String name;
    private String description;
    private String urlImage;
    private Status status;

    public CategoryResponseDTO(Long id, String categoryCode, String name, String description, String urlImage, Status status, Date createdDate, Date modifiedDate, String createdBy, String modifiedBy) {
        super(createdDate, modifiedDate, createdBy, modifiedBy);
        this.id = id;
        this.categoryCode = categoryCode;
        this.name = name;
        this.description = description;
        this.urlImage = urlImage;
        this.status = status;
    }
}
