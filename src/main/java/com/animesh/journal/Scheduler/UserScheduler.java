package com.animesh.journal.Scheduler;

import com.animesh.journal.Entity.JournalEntry;
import com.animesh.journal.Entity.User;
import com.animesh.journal.enums.Sentiment;
import com.animesh.journal.repositry.UserRepositryImpl;
import com.animesh.journal.services.EmailService;
import com.animesh.journal.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepositryImpl userRepositryImpl;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private EmailService emailService;

    @Scheduled( cron = "0 0 9 * * Sun")
    public void scheduled(){
        List<User> data=userRepositryImpl.getUserForSA();
        for(User user:data){
            List<JournalEntry> journalentry=user.getJournalEntries();
            List<Sentiment> filteredentries=journalentry.stream().filter(x -> x.getDatetime().isAfter((LocalDateTime.now().minus(7, ChronoUnit.DAYS)))).map(x-> x.getSentiment()).collect(Collectors.toList());
            emailService.sendEmail(user.getEmail(),"Sentiment Analysis For the Week",sentimentAnalysisService.sentimentAnalysis(filteredentries));
        }
    }

}
