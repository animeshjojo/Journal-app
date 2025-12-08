package com.animesh.journal.services;

import com.animesh.journal.Entity.User;
import com.animesh.journal.repositry.UserRepositry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepositry userRepositry;

    public List<User> getAll(){
        return userRepositry.findAll();
    }

    public void saveUser(User user){
        user.setDatetime(LocalDateTime.now());
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

}
