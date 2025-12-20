package com.animesh.journal.controller;

import com.animesh.journal.Entity.User;
import com.animesh.journal.api.response.WeatherResponse;
import com.animesh.journal.services.UserService;
import com.animesh.journal.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class Usercontroller {

    @Autowired
    private UserService userservice;

    @Autowired
    private WeatherService weatherservice;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User indB=userservice.findByUserName(username);
        indB.setUserName(user.getUserName().isEmpty()? username:user.getUserName());
        indB.setPassword(user.getPassword().isEmpty()? indB.getPassword():user.getPassword());
        if(user.getPassword().isEmpty()){
            userservice.saveUser(indB);
        }
        else{
            userservice.saveNewUser(indB);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        userservice.deletebyusername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public String greetings(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        WeatherResponse weatherResponse= weatherservice.getWeather("Kolkata");
        //later we can add a field city in user entity and fetch the city from the entity
        String greeting="";
        if(weatherResponse!=null){
            greeting="Today's Weather at Kolkata is: "+weatherResponse.current.getTemperature();
        }
        return "Welcome "+username+". "+greeting;
    }

}
