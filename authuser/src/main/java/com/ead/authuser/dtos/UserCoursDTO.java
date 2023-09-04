package com.ead.authuser.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UserCoursDTO {

    private UUID userId;

    @NotNull
    private UUID courseId;
}
