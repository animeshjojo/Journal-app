package com.animesh.journal.controller;

import com.animesh.journal.Entity.JournalEntry;
import com.animesh.journal.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalentryservice;

    @GetMapping
    public ResponseEntity<?> getall(){
        List<JournalEntry> data=journalentryservice.getall();
        if(data!=null && !data.isEmpty()){
            return new ResponseEntity<>(data,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> journalentry(@RequestBody JournalEntry myentry){
        try {
            myentry.setDatetime(LocalDateTime.now());
            journalentryservice.entry(myentry);
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

    @PutMapping("/id/{myid}")
    public ResponseEntity<?> update(@PathVariable ObjectId myid,@RequestBody JournalEntry newentry){
        journalentryservice.updatedata(myid,newentry);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deletentry(@PathVariable ObjectId myid){
        if(journalentryservice.findbyid(myid).orElse(null)==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            journalentryservice.deletebyid(myid);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
