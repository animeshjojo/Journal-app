package com.animesh.journal.controller;

import com.animesh.journal.Dto.UserDto;
import com.animesh.journal.Entity.User;
import com.animesh.journal.services.AIService;
import com.animesh.journal.services.UserService;
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
    private AIService  aiService;

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
    public ResponseEntity<String> login(@RequestBody UserDto userdto) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userdto.getUserName(), userdto.getPassword()));
            String jwt = jwtUtil.generateToken(userdto.getUserName());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public String getinfo(@RequestBody String prompt){
       return aiService.getinfo(prompt);
    }

    @GetMapping("/redirect")
    public ResponseEntity<?> redirect(){
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, "https://www.google.co.in")
                .build();
    }
}
