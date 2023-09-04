package com.ead.authuser.controllers;

import com.ead.authuser.model.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specification.UserSpecification;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
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
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted succefull");
    }
}
