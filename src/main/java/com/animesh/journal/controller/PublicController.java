package com.animesh.journal.controller;

import com.animesh.journal.Entity.User;
import com.animesh.journal.repositry.UserRepositry;
import com.animesh.journal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;



    @PostMapping
    public void saveUser(@RequestBody User user){
        userService.saveUser(user);
    }
}
