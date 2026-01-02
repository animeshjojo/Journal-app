package com.animesh.journal.controller;

import com.animesh.journal.Entity.User;
import com.animesh.journal.services.AIService;
import com.animesh.journal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @Autowired
    AIService  aiService;

    @GetMapping("health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("create-user")
    public ResponseEntity<?> saveUser(@RequestBody User user){

        boolean b=userService.saveNewUser(user);
        if(b){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Username already taken",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public String getinfo(@RequestBody String prompt){
       return aiService.getinfo(prompt);
    }
}
