package com.animesh.journal.controller;

import com.animesh.journal.Entity.User;
import com.animesh.journal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class Usercontroller {

    @Autowired
    private UserService userservice;

    @GetMapping
    public List<User> getall(){
       return  userservice.getall();
    }

    @PostMapping
    public void createuser(@RequestBody User user){
        userservice.createuser(user);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateuser(@RequestBody User user,@PathVariable String username){
        User indB=userservice.findByUserName(username);
        if(indB!=null){
            indB.setUserName(user.getUserName());
            indB.setPassword(user.getPassword());
            userservice.createuser(indB);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
