package com.ead.course.validation;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.models.UserModel;
import com.ead.course.models.enums.UserType;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseDTO courseDTO = (CourseDTO) target;
        validator.validate(courseDTO, errors);
        if(!errors.hasErrors()) {
            validateUserInstructor(courseDTO.getUserInstructor(), errors);
        }
    }

    private void validateUserInstructor(UUID userIdInstructor, Errors errors) {
        Optional<UserModel> userModelOptional = userService.findById(userIdInstructor);
        if(!userModelOptional.isPresent()) {
            errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found.");
        }
        if(userModelOptional.get().getUserId().equals(UserType.STUDENT.toString())) {
            errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.");
        }
    }
}
