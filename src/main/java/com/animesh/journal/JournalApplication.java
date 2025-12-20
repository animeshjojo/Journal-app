package com.animesh.journal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement //This find the block that has @Transaction annotations and make a container of it called transaction context. It performs commit and rollback
public class JournalApplication {

	public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
	}

    @Bean
    public PlatformTransactionManager func(MongoDatabaseFactory dbfactory){
        return new MongoTransactionManager(dbfactory);
    }
    /* @Transactional works only when Spring has a TransactionManager.
       MongoDB has no auto-configured manager, so defining MongoTransactionManager manually
       enables real multi-document transactions.

        MongoDatabaseFactory is an interface in Spring Data MongoDB that provides connections to a MongoDB database.
        Think of it as a bridge between Spring and MongoDB.

        Why Spring needs it?
        Spring does not talk directly to MongoDB.
        Instead, it asks MongoDatabaseFactory → “give me the DB connection”.
        MongoTransactionManager uses this factory to start/commit transactions.*/
       @Bean
       public RestTemplate restTemplate(){
           return new RestTemplate();
       }
}

//PlatformTransactionManager is an interface that contains this commit and rollback functions.
//MongoTransactionManager implements the above interface and does commit and rollback