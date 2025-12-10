package com.animesh.journal.controller;

import com.animesh.journal.Entity.User;
import com.animesh.journal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class Usercontroller {

    @Autowired
    private UserService userservice;

    @GetMapping
    public List<User> getAll(){
       return  userservice.getAll();
    }

    @PostMapping
    public void saveUser(@RequestBody User user){
        userservice.saveUser(user);
    }

    @PutMapping("/update-user}")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User indB=userservice.findByUserName(username);
        if(indB!=null){
            indB.setUserName(user.getUserName());
            indB.setPassword(user.getPassword());
            userservice.saveUser(indB);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
