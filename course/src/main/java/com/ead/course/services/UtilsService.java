package com.ead.course.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {

    String createURL(Pageable pageable, UUID userId);

    String buildURL(String requestURL, String uriPath, Pageable pageable);

    String buildURL(String requestURL, String uriPath);
}
