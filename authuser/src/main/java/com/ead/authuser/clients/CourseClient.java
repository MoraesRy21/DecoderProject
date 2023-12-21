package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDTO;
import com.ead.authuser.dtos.ResponsePageDTO;
import com.ead.authuser.services.UtilsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${ead.api.url.course}")
    private String REQUEST_URI_COURSE;

//    @Retry(name = "retryInstance", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "circuitbreakerInstance", fallbackMethod = "circuitbreakerFallback")
    public Page<CourseDTO> getAllCoursesByUsers(Pageable pageable, UUID userId) {
        List<CourseDTO> searchResult = null;
        ResponseEntity<ResponsePageDTO<CourseDTO>> result = null;
        String url = utilsService.buildURL(REQUEST_URI_COURSE, "/courses?userId=" + userId, pageable);

        String logRequestURLMessage = "Request URL: {"+url+"} ";
        log.debug(logRequestURLMessage);
        log.info(logRequestURLMessage);
        try {
            ParameterizedTypeReference<ResponsePageDTO<CourseDTO>> responseType = new ParameterizedTypeReference() {};
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Responese Number of Elements {} ", searchResult.size());
        } catch(HttpStatusCodeException e) {
            log.error("Error request /courses {} ", e);
        }
        log.info("Ending request /courses userId {} ", userId);
        return result.getBody();
    }

    public Page<CourseDTO> retryFallback(Pageable pageable, UUID userId, Throwable throwable) {

        return new PageImpl<>(new ArrayList<>());
    }

    public Page<CourseDTO> circuitbreakerFallback(Pageable pageable, UUID userId, Throwable throwable) {
        return new PageImpl<>(new ArrayList<>());
    }

    public void deleteCourseUserByUser(UUID userId) {
        String url = utilsService.buildURL(REQUEST_URI_COURSE, "/courses?users/" + userId);
        restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}
