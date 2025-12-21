package com.animesh.journal.services;

import com.animesh.journal.Entity.JournalEntry;
import com.animesh.journal.Entity.User;
import com.animesh.journal.repositry.JournalEntryRepositry;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepositry journalentryrepositry;

    @Autowired
    private UserService userservice;


    public List<JournalEntry> getAll(){
        return journalentryrepositry.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntry myEntry, String username){
        try{
            User user=userservice.findByUserName(username);
            myEntry.setDatetime(LocalDateTime.now());
            JournalEntry saved=journalentryrepositry.save(myEntry); //we are taking this instead of myEntry because myEntry doesn't have_id so after saving _id is generated
            user.getJournalEntries().add(saved);
        /* suppose after the above line exception occurs or error due to any code. For ex if we set username to null.
           So the next line that is to save the user will not take place in the db. This doesnt follow atomicity
           To handle this we make the while method as a Transaction. So either the whole block will be executed or none*/
            userservice.saveUser(user);
        }
        catch(Exception e){
            throw new RuntimeException("Some exception occurred during saving entries");
        }


    }

    public Optional<JournalEntry> findbyid(ObjectId myid){
       return journalentryrepositry.findById(myid);
    }

    public void updatedata(ObjectId myid, JournalEntry newentry, String username){
        JournalEntry old=journalentryrepositry.findById(myid).orElse(null);
        if(old!=null) {
            old.setContent(newentry.getContent()!=null && !newentry.getContent().isEmpty() ? newentry.getContent() : old.getContent());
            old.setTitle(!newentry.getTitle().isEmpty() ? newentry.getTitle() : old.getTitle());
            journalentryrepositry.save(old);
        }
        else{
            saveEntry(newentry,username);
        }

    }

    @Transactional
    public boolean deletebyid(ObjectId myid, String username)
    {
        boolean flag=false;
        try {
            User user=userservice.findByUserName(username);
            flag=user.getJournalEntries().removeIf(x-> x.getId().equals(myid));
        /* The above line is used to delete the reference instantly when the journal is getting deleted from the journal_entries.
         If we don't do it then in mongsh the jornal entry will be deleted from the journal_entries but the referncce still be
          showing inside the user however after a next save on the user the dirty read will get fixed and the id will be delted
          i.e from next save the data will be consistent. This instant delete on both the collections is not happening because
          mongodb doesnot support cascading delete whereas relational database support it  */

            if(flag) {
                userservice.saveUser(user);
                journalentryrepositry.deleteById(myid);
            }
        }
        catch (Exception e){
            throw new RuntimeException("Some exception occurred during deleting entries");
        }
        return flag;


    }


}
