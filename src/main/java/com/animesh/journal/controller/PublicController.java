package com.animesh.journal.controller;

import com.animesh.journal.Dto.LoginResponseDto;
import com.animesh.journal.Dto.UserDto;
import com.animesh.journal.Entity.User;
import com.animesh.journal.api.response.WeatherResponse;
import com.animesh.journal.services.AIService;
import com.animesh.journal.services.UserService;
import com.animesh.journal.services.WeatherService;
import com.animesh.journal.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("SignUp")
    public ResponseEntity<?> saveUser(@RequestBody User user){

        boolean b=userService.saveNewUser(user);
        if(b){
            return new ResponseEntity<>(user.getUserName()+" Created",HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Username already taken",HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userdto) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userdto.getUserName(), userdto.getPassword()));
            String jwt = jwtUtil.generateToken(userdto.getUserName());
            User user=userService.findByUserName(userdto.getUserName());
            WeatherResponse weatherResponse= weatherService.getWeather(user.getCity());
            String message="Hi "+user.getUserName()+"!";
            if(weatherResponse!=null){
                message="Hi "+user.getUserName()+"!"+" Today's weather at "+user.getCity()+"is "+weatherResponse.getCurrent().getTemperature();
            }
            return new ResponseEntity<>(new LoginResponseDto(message,jwt), HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

}
