package com.jeff.controller;

import com.jeff.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/userlogin")
@SessionAttributes(types = User.class, value = "User")
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/login")
    public String login(HttpSession session) {
        System.out.println("login");
        User user = new User();
        String id = String.valueOf(ThreadLocalRandom.current().nextInt(20));
        user.setId(id);
        System.out.println(user.getId());
        session.setAttribute("User", user);
        System.out.println("done");
        return "logged-in";
    }

    @GetMapping("/check")
    public String check(@SessionAttribute("User") User user, SessionStatus status) {
        System.out.println("check");
        System.out.println(user.getId());
        return user.getId();
    }
}
