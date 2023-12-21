package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {

    @Query(value = """
            SELECT
                CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END 
            FROM courses_users cu
            WHERE cu.course_id = :courseId AND cu.user_id = :userId
            """, nativeQuery = true)
    boolean existsByCourseAndUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    @Modifying
    @Query(value = "INSERT INTO courses_users VALUES(:courseId, :userId)", nativeQuery = true)
    void saveCourseUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    @Modifying
    void deleteCourseUserByCourse(@Param("courseId") UUID courseId);

    @Modifying
    void deleteCourseUserByUser(@Param("userId") UUID userId);
}
