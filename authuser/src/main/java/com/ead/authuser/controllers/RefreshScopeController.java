package com.ead.authuser.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class RefreshScopeController {

    @Value("${athuser.refreshscope.name}")
    private String name;

    @GetMapping("/refreshscope")
    public ResponseEntity<String> refreshScope() {
        return ResponseEntity.ok(name);
    }
}
