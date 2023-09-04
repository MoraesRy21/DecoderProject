package com.ead.course.services;

import com.ead.course.models.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {

    List<LessonModel> findAll();

    Page<LessonModel> findAll(Specification spec, Pageable pageable);

    List<LessonModel> findAllByModule(UUID moduleId);

    Page<LessonModel> findAllByModule(Specification spec, Pageable pageable);

    Optional<LessonModel> findById(UUID lessonId);

    Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);

    LessonModel save(LessonModel lessonModel);

    void delete(LessonModel lessonModel);
}
