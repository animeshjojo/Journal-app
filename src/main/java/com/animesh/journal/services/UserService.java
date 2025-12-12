package com.animesh.journal.services;

import com.animesh.journal.Entity.User;
import com.animesh.journal.repositry.UserRepositry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepositry userRepositry;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<User> getAll(){
        return userRepositry.findAll();
    }

    public void saveNewUser(User user){
        user.setDatetime(LocalDateTime.now());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
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
