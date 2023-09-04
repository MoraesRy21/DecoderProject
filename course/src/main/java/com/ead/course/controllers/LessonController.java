package com.ead.course.controllers;

import com.ead.course.dtos.LessonDTO;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.LessonSpecification;
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
public class LessonController {

    @Autowired
    LessonService lessonService;

    @Autowired
    ModuleService moduleService;

    @GetMapping("/lessons")
    public ResponseEntity<Page<LessonModel>> getAllLessons(LessonSpecification spec,
                                                           @PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC)
                                                           Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAll(spec, pageable));
    }

    @GetMapping("/lessons/{lessonId}")
    public ResponseEntity<Object> getOneModule(@PathVariable(value = "lessonId") UUID lessonId) {
        Optional<LessonModel> lessonModelOptional = lessonService.findById(lessonId);
        if(lessonModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson Not Found.");
        return ResponseEntity.status(HttpStatus.FOUND).body(lessonModelOptional.get());
    }

    @GetMapping("/modules/{lessonId}/lessons")
    public ResponseEntity<Page<LessonModel>> getAllModulesByCourse(@PathVariable(value = "moduleId") UUID moduleId,
                                                                   LessonSpecification spec,
                                                                   @PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC)
                                                                   Pageable pageable) {
        Specification specification = LessonSpecification.lessonModuleId(moduleId).and(spec);
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllByModule(specification, pageable));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> getOneModuleByCourse(@PathVariable(value = "moduleId") UUID moduleId,
                                                       @PathVariable(value = "lessonId") UUID lessonId) {
        Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if(lessonModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson or Module Not Found!");
        return ResponseEntity.status(HttpStatus.OK).body(lessonModelOptional.get());
    }

    @PostMapping("/modules/{moduleId}/lesson")
    public ResponseEntity<Object> saveLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                             @RequestBody @Valid LessonDTO lessonDTO) {
        Optional<ModuleModel> moduleModelOptional = moduleService.findById(moduleId);
        if(moduleModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found!");
        var lessonModel = new LessonModel();
        BeanUtils.copyProperties(lessonDTO, lessonModel);
        lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lessonModel.setModule(moduleModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonModel));
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> updateLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId,
                                               @RequestBody @Valid LessonDTO lessonDTO) {
        Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if(lessonModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson or Module Not Found!");
        var lessonModel = new LessonModel();
        BeanUtils.copyProperties(lessonDTO, lessonModel);
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lessonModel));
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable Map<String, UUID> pathVarsMap) {
        Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(
                pathVarsMap.get("moduleId"), pathVarsMap.get("lessonId"));
        if(lessonModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson or Module Not Found!");
        lessonService.delete(lessonModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted with successfully!");
    }
}
