package com.animesh.journal.repositry;

import com.animesh.journal.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query=new Query();
        Criteria criteria=new Criteria();
        query.addCriteria(criteria.andOperator(Criteria.where("sentimentAnalysis").is(true),
                Criteria.where("email").exists(true),
                Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,6}$")));
        return mongoTemplate.find(query,User.class);
    }
}
