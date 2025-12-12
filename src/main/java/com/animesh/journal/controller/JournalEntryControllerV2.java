package com.animesh.journal.controller;

import com.animesh.journal.Entity.JournalEntry;
import com.animesh.journal.Entity.User;
import com.animesh.journal.services.JournalEntryService;
import com.animesh.journal.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalentryservice;

    @Autowired
    private UserService userservice;

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userservice.findByUserName(username);
        List<JournalEntry> data=user.getJournalEntries();
        if(data!=null && !data.isEmpty()){
            return new ResponseEntity<>(data,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> journalEntry(@RequestBody JournalEntry myEntry){
        try {
            String username=SecurityContextHolder.getContext().getAuthentication().getName();
            journalentryservice.entry(myEntry,username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getbyid(@PathVariable ObjectId myid){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userservice.findByUserName(username);
        boolean ispresent=user.getJournalEntries().stream().anyMatch(x->x.getId().equals(myid));
        if(ispresent){
            Optional<JournalEntry> data=journalentryservice.findbyid(myid);
            return new ResponseEntity<>(data.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/id/{myid}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId myid,@RequestBody JournalEntry newentry){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        User user=userservice.findByUserName(username);
        boolean ispresent=user.getJournalEntries().stream().anyMatch(x->x.getId().equals(myid));
        if(ispresent || !journalentryservice.findbyid(myid).isPresent()){
            journalentryservice.updatedata(myid,newentry,username);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myid){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
            boolean flag=journalentryservice.deletebyid(myid,username);
            if(flag){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

}
