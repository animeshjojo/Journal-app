package com.animesh.journal.repositry;

import com.animesh.journal.Entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositryImpl {


    private MongoTemplate mongoTemplate;


    public UserRepositryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<User> getUserForSA(){
        Query query=new Query();
        Criteria criteria=new Criteria();
        query.addCriteria(criteria.andOperator(Criteria.where("sentimentAnalysis").is(true),
                Criteria.where("email").exists(true),
                Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,6}$")));
        return mongoTemplate.find(query,User.class);
    }
}
