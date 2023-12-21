package com.ead.authuser.specification;

import com.ead.authuser.model.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "userType", spec = Equal.class),
        @Spec(path = "userStatus", spec = Equal.class),
        @Spec(path = "email", spec = Like.class),
        @Spec(path = "fullName", spec = Like.class)
})
public interface UserSpecification extends Specification<UserModel> {

//    static Specification<UserModel> userCourseId(final UUID courseID) {
//        return (root, query, criteriaBuilder) ->  { // toPredicate
//            query.distinct(true);
//            Join<UserModel, UserCourseModel> userProd = root.join("usersCourses");
//            return criteriaBuilder.equal(userProd.get("courseId"), courseID);
//        };
//    }
}
