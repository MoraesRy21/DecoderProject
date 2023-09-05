package com.ead.course.specifications;

import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import jakarta.persistence.criteria.Join;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@And({
        @Spec(path = "courseLevel", spec = Equal.class),
        @Spec(path = "courseStatus", spec = Equal.class),
        @Spec(path = "name", spec = Like.class)
})
public interface CourseSpecification extends Specification<CourseModel> {

}
