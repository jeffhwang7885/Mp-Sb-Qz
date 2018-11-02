package com.jeff.controller;


import com.jeff.model.User;
import com.jeff.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/async")
public class AsyncController {

    private static Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    UserService service;

    @RequestMapping("/info")
    public String getInfo() {
        try {
            CompletableFuture<User> user = service.createUser();
            CompletableFuture<User> user1 = service.createUser();
            CompletableFuture<User> user2 = service.createUser();
            CompletableFuture.allOf(user, user1, user2).join();
            logger.info(user1.get().getId());
            logger.info(user.get().getId());
            logger.info(user2.get().getId());

            return "Success";
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "failed";
    }

    @RequestMapping("/sync")
    public String get() {
        User user = service.getUser();
        User user1 = service.getUser();
        User user2 = service.getUser();

        logger.info(user.getId());
        logger.info(user1.getId());
        logger.info(user2.getId());
        return "Success";
    }

}
