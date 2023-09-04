package com.ead.course.dtos;

import com.ead.course.models.enums.CourseLevel;
import com.ead.course.models.enums.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CourseDTO {

    @NotBlank
    private String name;
    @NotNull
    private String description;
    private String imageURL;
    @NotNull
    private CourseStatus courseStatus;
    @NotNull
    private CourseLevel courseLevel;
    @NotNull
    private UUID userInstructor;
}
