package com.ead.course.dtos;

import com.ead.course.models.enums.UserStatus;
import com.ead.course.models.enums.UserType;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

    private UUID userId;
    private String username;
    private String email;
    private UserStatus userStatus;
    private UserType userType;
    private String fullName;
    private String phoneNumber;
    private String cpf;
    private String imageURL;

}
