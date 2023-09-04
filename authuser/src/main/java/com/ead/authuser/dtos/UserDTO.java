package com.ead.authuser.dtos;


import com.ead.authuser.validation.UsernameContraintValidation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    public interface UserView {
        interface ResgistrationPost {}
        interface UserPut {}
        interface PasswordPut {}
        interface ImagePut {}
    }

    private UUID userId;

    @NotBlank(groups = UserView.ResgistrationPost.class)
    @Size(min = 4, max = 50, groups = UserView.ResgistrationPost.class)
    @UsernameContraintValidation(groups = UserView.ResgistrationPost.class)
    @JsonView(UserView.ResgistrationPost.class)
    private String username;

    @Email(groups = UserView.ResgistrationPost.class)
    @NotBlank(groups = UserView.ResgistrationPost.class)
    @JsonView(UserView.ResgistrationPost.class)
    private String email;

    @NotBlank(groups = {UserView.ResgistrationPost.class, UserView.PasswordPut.class})
    @Size(min = 8, max = 20, groups = {UserView.ResgistrationPost.class, UserView.PasswordPut.class})
    @JsonView({UserView.ResgistrationPost.class, UserView.PasswordPut.class})
    private String password;

    @NotBlank(groups = UserView.PasswordPut.class)
    @Size(min = 8, max = 20, groups = UserView.PasswordPut.class)
    @JsonView(UserView.PasswordPut.class)
    private String oldPassword;

    @JsonView({UserView.ResgistrationPost.class, UserView.UserPut.class})
    private String fullName;

    @JsonView({UserView.ResgistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;

    @JsonView({UserView.ResgistrationPost.class, UserView.UserPut.class})
    private String cpf;

    @NotBlank(groups = UserView.ImagePut.class)
    @JsonView(UserView.ImagePut.class)
    private String imageURL;
}
