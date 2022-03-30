package com.hansybx.clovedoctorbe.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class userController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
