package com.animesh.journal.repositry;

import com.animesh.journal.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepositry extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
}
