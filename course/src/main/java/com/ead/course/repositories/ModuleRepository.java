package com.ead.course.repositories;

import com.ead.course.models.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor {

    @Query(value="SELECT * FROM modules course_course_id = :courseId AND mo", nativeQuery = true)
    List<ModuleModel> findAllModulesIntoCourses(@Param("courseId") UUID courseId);

    @Query(value="SELECT * FROM modules course_course_id = :courseId", nativeQuery = true)
    List<ModuleModel> findAll(UUID courseId);

    @Query(value = "SELECT * FROM module JOIN  WHERE course_course_id = :courseId AND module_id = :moduleId", nativeQuery = true)
    Optional<ModuleModel> findModuleIntoCourse(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);
}
