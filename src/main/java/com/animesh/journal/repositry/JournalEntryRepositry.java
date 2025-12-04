package com.animesh.journal.repositry;

import com.animesh.journal.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface JournalEntryRepositry extends MongoRepository<JournalEntry, ObjectId> {
}
