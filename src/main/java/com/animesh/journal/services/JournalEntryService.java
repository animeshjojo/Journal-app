package com.animesh.journal.services;

import com.animesh.journal.Entity.JournalEntry;
import com.animesh.journal.repositry.JournalEntryRepositry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepositry journalentryrepositry;

    public List<JournalEntry> getall(){
        return journalentryrepositry.findAll();
    }

    public void entry(JournalEntry myentry){
        myentry.setDatetime(LocalDateTime.now());
        journalentryrepositry.save(myentry);
    }

    public Optional<JournalEntry> findbyid(ObjectId myid){
       return journalentryrepositry.findById(myid);
    }

    public void updatedata(ObjectId myid,JournalEntry newentry){
        JournalEntry old=journalentryrepositry.findById(myid).orElse(null);
        if(old!=null) {
            old.setContent(newentry.getContent()!=null && !newentry.getContent().isEmpty() ? newentry.getContent() : old.getContent());
            old.setTitle(newentry.getTitle()!=null && !newentry.getTitle().isEmpty() ? newentry.getTitle() : old.getTitle());
            journalentryrepositry.save(old);
        }
        else{
            journalentryrepositry.save(newentry);
        }

    }

    public void deletebyid(ObjectId myid){
        journalentryrepositry.deleteById(myid);
    }


}
