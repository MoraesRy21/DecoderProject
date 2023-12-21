package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDTO;
import com.ead.authuser.model.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specification.UserSpecification;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /*@GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(UserSpecification specification,
                                                       @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC)
                                                       Pageable pageable,
                                                       @RequestParam(required = false) UUID courseID) {
        Page<UserModel> userModelPage;
        if(courseID != null) {
            Specification<UserModel> userModelSpecification = UserSpecification.userCourseId(courseID);
            userModelPage = userService.findAll(userModelSpecification.and(specification), pageable);
        }else {
            userModelPage = userService.findAll(specification, pageable);
        }
        if(!userModelPage.isEmpty()) {
            for(UserModel user : userModelPage.toList()) {
                ResponseEntity userModelResponse = WebMvcLinkBuilder.methodOn(UserController.class).getOneUser(user.getUserId());
                Link link = WebMvcLinkBuilder.linkTo(userModelResponse).withSelfRel();
                user.add(link);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }*/

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }

    @PutMapping("/{userId}/user")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @Validated(UserDTO.UserView.UserPut.class)
                                             @JsonView(UserDTO.UserView.UserPut.class) UserDTO userDTO) {
        log.debug("PUT updateUser userDTO received {}", userDTO.toString());
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        var userModel = userModelOptional.get();
        userModel.setFullName(userDTO.getFullName());
        userModel.setPhoneNumber(userDTO.getPhoneNumber());
        userModel.setCpf(userDTO.getCpf());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.updateUser(userModel);
        log.debug("PUT updateUser userId saved {}", userDTO.getUserId());
        log.info("PUT updated successfully userId {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody @Validated(UserDTO.UserView.PasswordPut.class)
                                                 @JsonView(UserDTO.UserView.PasswordPut.class) UserDTO userDTO) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        if(!userModelOptional.get().getPassword().equals(userDTO.getOldPassword()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Missmacth old password!");
        var userModel = userModelOptional.get();
        userModel.setPassword(userDTO.getPassword());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.updatePassword(userModel);
        return ResponseEntity.status(HttpStatus.OK).body("Password update successfully");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                               @RequestBody @Validated(UserDTO.UserView.ImagePut.class)
                                               @JsonView(UserDTO.UserView.ImagePut.class) UserDTO userDTO) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        var userModel = userModelOptional.get();
        userModel.setImageURL(userDTO.getImageURL());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.updateUser(userModel);
        return ResponseEntity.status(HttpStatus.OK).body("Password update successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        log.debug("DELETE deleteUser userId received {}", userId);
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        userService.deleteUser(userModelOptional.get());
        log.debug("DELETE deleteUser userId deleted {}", userId);
        log.info("DELETE deleteUser successfully userId {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }
}
