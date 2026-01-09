package com.animesh.journal.controller;

import com.animesh.journal.Entity.User;
import com.animesh.journal.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userservice;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> data=userservice.getAll();
        if(data!=null){
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-admin")
    public ResponseEntity<Response> createAdmin(@RequestBody User user){
        userservice.createAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
