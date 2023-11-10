package com.douzone.surveymanagement.surveystatus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Some description here.
 *
 * @author : 강명관
 * @since : 1.0
 **/

@RestController
public class TestController {

    @GetMapping("/deploy/test")
    public String test() {
        return "Success Deploy!!";
    }
}
