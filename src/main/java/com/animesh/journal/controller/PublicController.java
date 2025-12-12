package com.animesh.journal.controller;

import com.animesh.journal.Entity.User;
import com.animesh.journal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;


    @GetMapping("health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("create-user")
    public void saveUser(@RequestBody User user){
        userService.saveNewUser(user);
    }
}
