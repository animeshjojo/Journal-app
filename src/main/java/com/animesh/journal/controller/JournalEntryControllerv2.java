package com.animesh.journal.controller;

import com.animesh.journal.Entity.JournalEntry;
import com.animesh.journal.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalentryservice;

    @GetMapping
    public List<JournalEntry> getall(){
        return journalentryservice.getall();
    }

    @PostMapping
    public boolean journalentry(@RequestBody JournalEntry myentry){
        myentry.setDatetime(LocalDateTime.now());
        journalentryservice.entry(myentry);
        return true;
    }

    @GetMapping("/id/{myid}")
    public JournalEntry getbyid(@PathVariable ObjectId myid){
        return journalentryservice.findbyid(myid).orElse(null);
    }

    @PutMapping("/id/{myid}")
    public JournalEntry update(@PathVariable ObjectId myid,@RequestBody JournalEntry newentry){
        journalentryservice.updatedata(myid,newentry);
        return journalentryservice.findbyid(myid).orElse(null);
    }

    @DeleteMapping("id/{myid}")
    public String deletentry(@PathVariable ObjectId myid){
        if(journalentryservice.findbyid(myid).orElse(null)==null){
            return "No data found";
        }
        else{
            journalentryservice.deletebyid(myid);
            return "Entry Deleted";
        }
    }
}
