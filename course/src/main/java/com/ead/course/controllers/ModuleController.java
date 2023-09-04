package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDTO;
import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.ModuleSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @Autowired
    CourseService courseService;

    @GetMapping("/modules")
    public ResponseEntity<Page<ModuleModel>> getAllModules(ModuleSpecification spec,
                                                           @PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAll(spec, pageable));
    }

    @GetMapping("/modules/{moduleId}")
    public ResponseEntity<Object> getOneModule(@PathVariable(value = "moduleId")
                                               UUID moduleId) {
        Optional<ModuleModel> moduleModelOptional = moduleService.findById(moduleId);
        if(moduleModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
        return ResponseEntity.status(HttpStatus.FOUND).body(moduleModelOptional.get());
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<Page<ModuleModel>> getAllModulesByCourse(@PathVariable(value = "courseId") UUID courseId, ModuleSpecification spec,
                                                                   @PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC)
                                                                   Pageable pageable) {
        Specification specification = ModuleSpecification.moduleCourseId(courseId).and(spec);
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAllByModule(specification, pageable));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getOneModuleByCourse(@PathVariable(value = "courseId") UUID courseId,
                                               @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if(moduleModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module or Course Not Found!");
        return ResponseEntity.status(HttpStatus.OK).body(moduleModelOptional.get());
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> saveModule(@PathVariable(value = "courseId") UUID courseId,
                                             @RequestBody @Valid ModuleDTO moduleDTO) {
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if(courseModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found!");
        var moduleModel = new ModuleModel();
        BeanUtils.copyProperties(moduleDTO, moduleModel);
        moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        moduleModel.setCourse(courseModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(moduleModel));
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> updateModule(@PathVariable(value = "courseId") UUID courseId,
                                               @PathVariable(value = "moduleId") UUID moduleId,
                                               @RequestBody @Valid ModuleDTO moduleDTO) {
        Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(courseId, moduleId);
        if(moduleModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module or Course Not Found!");
        var moduleModel = new ModuleModel();
        BeanUtils.copyProperties(moduleDTO, moduleModel);
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(moduleModel));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable Map<String, UUID> pathVarsMap) {
        Optional<ModuleModel> moduleModelOptional = moduleService.findModuleIntoCourse(
                pathVarsMap.get("courseId"), pathVarsMap.get("moduleId"));
        if(moduleModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module or Course Not Found!");
        moduleService.delete(moduleModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Module deleted with successfully!");
    }
}
