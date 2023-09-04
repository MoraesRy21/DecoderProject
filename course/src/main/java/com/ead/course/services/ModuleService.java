package com.ead.course.services;

import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {

    List<ModuleModel> findAll();

    Page<ModuleModel> findAll(Specification spec, Pageable pageable);

    List<ModuleModel> findAllByModule(UUID courseId);

    Page<ModuleModel> findAllByModule(Specification spec, Pageable pageable);

    Optional<ModuleModel> findById(UUID moduleId);

    ModuleModel save(ModuleModel moduleModel);

    void delete(ModuleModel moduleModel);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

}
