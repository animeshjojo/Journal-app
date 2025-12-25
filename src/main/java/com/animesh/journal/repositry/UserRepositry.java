package com.animesh.journal.repositry;

import com.animesh.journal.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


public interface UserRepositry extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
    void deleteByUserName(String username);

    //to create custom query like to find username having sentimentAnalysis as true we have to use criteria method.
    //check UserRepositryImpl to know more about custom query.
}
