package com.animesh.journal.controller;

import com.animesh.journal.Entity.JournalEntry;
import com.animesh.journal.Entity.User;
import com.animesh.journal.services.JournalEntryService;
import com.animesh.journal.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalentryservice;

    @Autowired
    private UserService userservice;

    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
        User user=userservice.findByUserName(username);
        List<JournalEntry> data=user.getJournalEntries();
        if(data!=null && !data.isEmpty()){
            return new ResponseEntity<>(data,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{username}")
    public ResponseEntity<?> journalEntry(@RequestBody JournalEntry myEntry,@PathVariable String username){
        try {
            journalentryservice.entry(myEntry,username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getbyid(@PathVariable ObjectId myid){

        Optional<JournalEntry> data=journalentryservice.findbyid(myid);
        if(data.isPresent()){
            return new ResponseEntity<>(data.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/id/{username}/{myid}")
    public ResponseEntity<?> update(@PathVariable ObjectId myid,@RequestBody JournalEntry newentry,@PathVariable String username){
        journalentryservice.updatedata(myid,newentry,username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("id/{username}/{myid}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myid,@PathVariable String username){
        if(journalentryservice.findbyid(myid).orElse(null)==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            journalentryservice.deletebyid(myid,username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
