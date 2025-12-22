package com.animesh.journal.services;

import com.animesh.journal.Entity.User;
import com.animesh.journal.repositry.UserRepositry;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
//Logging is the process of recording application activities and errors to help developers monitor and debug the software.
/* SLF4J
What it is: A logging API (interface), not a real logger
Full form: Simple Logging Facade for Java
Role: Acts like a remote control
Does NOT write logs itself
Works with Logback, Log4j2, java.util.logging, etc. frameworks
Logback is the default logging implementation that Spring Boot auto-configures for SLF4J.
Logback is not a class implementing SLF4J, but a framework that provides SLF4J bindings.
*/
public class UserService {

    @Autowired
    private UserRepositry userRepositry;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static final Logger logger = LoggerFactory.getLogger(UserService.class);
    //It creates a class-specific logger so every log message is tagged with JournalEntryService in the log output.

    public List<User> getAll(){
        return userRepositry.findAll();
    }

    public boolean saveNewUser(User user){
        try{
            user.setDatetime(LocalDateTime.now());
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepositry.save(user);
            return true;
        }
        catch(Exception e){
            //logger.error("Error saving user",e);
           // log.warn("Error saving user {} ",user.getUserName());
            log.error("Error saving user {} ",user.getUserName());
            //log.trace("Error saving user {} ",user.getUserName());// trace and debug are not enabled by default. We have to enable it in application properties
            //log.debug("Error saving user {} ",user.getUserName());
            return false;
        }

    }
    public void createAdmin(User user){
        user.setDatetime(LocalDateTime.now());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepositry.save(user);
    }

    public void saveUser(User user){
        userRepositry.save(user);
    }

    public Optional<User> findbyid(ObjectId myid){
       return userRepositry.findById(myid);
    }


    public void deletebyid(ObjectId myid){
        userRepositry.deleteById(myid);
    }

    public User findByUserName(String UserName){
        return userRepositry.findByUserName(UserName);
    }

    public void deletebyusername(String username){ userRepositry.deleteByUserName(username); }

}
