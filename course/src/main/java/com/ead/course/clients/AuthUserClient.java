package com.ead.course.clients;

import com.ead.course.dtos.CourseUserDTO;
import com.ead.course.dtos.ResponsePageDTO;
import com.ead.course.dtos.UserDTO;
import com.ead.course.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class AuthUserClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${ead.api.url.authuser}")
    private String REQUEST_URI_AUTHUSER;

    public Page<UserDTO> getAllUsersByCourse(Pageable pageable, UUID courseId) {
        List<UserDTO> searchResult = null;
        ResponseEntity<ResponsePageDTO<UserDTO>> result = null;
        String url = utilsService.buildURL(REQUEST_URI_AUTHUSER, "/users?courseId=" + courseId, pageable);

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);
        try {
            ParameterizedTypeReference<ResponsePageDTO<UserDTO>> responseType = new ParameterizedTypeReference() {
            };
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Responese Number of Elements {} ", searchResult.size());
        } catch (HttpStatusCodeException e) {
            log.error("Error request /users {} ", e);
        }
        log.info("Ending request /users courseId {} ", courseId);
        return result.getBody();
    }

    public ResponseEntity<UserDTO> getOneUserById(UUID userId) {
        String url = utilsService.buildURL(REQUEST_URI_AUTHUSER, "/users/" + userId);
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
    }

    public void postSubscriptionUserInCourse(UUID courseId, UUID userId) {
        String url = utilsService.buildURL(REQUEST_URI_AUTHUSER, "/users?courseId=" + courseId + "/courses/subscription");

        var courseUserDTO = new CourseUserDTO(courseId, userId);
        restTemplate.postForObject(url, courseUserDTO, String.class);
    }

    public void deleteCourseInAuthUser(UUID courseId) {
        String url = utilsService.buildURL(REQUEST_URI_AUTHUSER, "/users/courses/"+courseId);
        restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}
