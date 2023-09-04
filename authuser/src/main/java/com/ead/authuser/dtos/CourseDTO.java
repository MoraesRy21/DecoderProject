package com.ead.authuser.dtos;

import com.ead.authuser.model.enums.CourseLevel;
import com.ead.authuser.model.enums.CourseStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class CourseDTO {

    private UUID courseId;
    private String name;
    private String description;
    private String imageURL;
    private CourseStatus courseStatus;
    private CourseLevel courseLevel;
    private UUID userInstructor;
}
