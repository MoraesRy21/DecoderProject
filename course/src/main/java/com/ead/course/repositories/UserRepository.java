package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);

    boolean existsByUserId(UUID userId);

    void deleteAllByUserId(UUID userId);
}
