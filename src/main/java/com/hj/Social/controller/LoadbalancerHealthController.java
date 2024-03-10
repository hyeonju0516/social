package com.hj.Social.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aws")
public class LoadbalancerHealthController {
    @GetMapping("")
    public ResponseEntity<?> successHealthCheck() {
    	return new ResponseEntity<>(HttpStatus.OK);
    }
}