package com.jeff.service;

import com.jeff.model.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {

    public User getUser() {
        System.out.println("In thread");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = new User();
        int i = ThreadLocalRandom.current().nextInt(10);
        user.setId(String.valueOf(i));
        return user;
    }

    @Async
    public CompletableFuture<User> createUser() {
        try {
            System.out.println("In thread");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = new User();
        int i = ThreadLocalRandom.current().nextInt(10);
        user.setId(String.valueOf(i));

        return CompletableFuture.completedFuture(user);
    }
}
