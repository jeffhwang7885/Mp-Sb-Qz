package com.jeff.service;

import com.jeff.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getUser() {
        User user = new User();
        user.setAge(29);
        user.setName("Jeff");
        return user;
    }
}
