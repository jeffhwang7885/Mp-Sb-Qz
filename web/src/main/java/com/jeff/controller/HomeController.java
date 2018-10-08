package com.jeff.controller;

import com.jeff.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserService userService;

    @Value("${spring.application.name}")
    private String environ;

    @Value("${test.tesName}")
    private String test;

    @Autowired
    private Environment env;

    @GetMapping("/")
    public String index() {
        MDC.put("userId", "1");
        logger.info("In index");
        return "Hello World";
    }

    @GetMapping("/profile")
    public String profile() {
        MDC.put("userId", "2");
        logger.info("In profile");
        return env.getProperty("spring.application.name");
    }

    @GetMapping("/test")
    public String test() {
        MDC.put("userId", "3");
        logger.info("In Test");
        return env.getProperty("test.tesName");
    }
}
