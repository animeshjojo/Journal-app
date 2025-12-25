package com.animesh.journal.repositry;

import com.animesh.journal.Entity.ConfigJournalApp;
import com.animesh.journal.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ConfigJournalAppRepositry extends MongoRepository<ConfigJournalApp, ObjectId> {
}
