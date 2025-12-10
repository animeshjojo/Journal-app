package com.animesh.journal.Entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true) //this will ensure every username is unique
    @NonNull // Lombok property that ensure that the fiels is notnull
    private String userName;
    @NonNull
    private String password;
    private LocalDateTime datetime;
    @DBRef //act as a foreign key. establishes a connection between two collection.
    /*
      here in the list, it does not store all the data of the journal entry
      like title content
      it stores journalEntries={
        DBref("journal_Entries", ObjectId("fwef324")),
        DBref("journal_Entries", ObjectId("f7ef324"))
        .                                     .
        .                                this is the object id of the journal associated to the user
      }
    */
    private List<JournalEntry> journalEntries=new ArrayList<>();
    //new arraylist will ensure that the list is empty not null when a user will be initialised
    private List<String> roles;
}
