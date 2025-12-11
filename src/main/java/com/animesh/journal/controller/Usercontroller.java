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


    @PutMapping
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

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        userservice.deletebyusername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
