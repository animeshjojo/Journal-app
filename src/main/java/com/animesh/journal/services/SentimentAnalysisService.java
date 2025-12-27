package com.animesh.journal.services;

import org.checkerframework.checker.units.qual.C;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

    public String  sentimentAnalysis(String data){
        return "Sentiment Analysis"; //here we will implement ai to analyse the sentiment
    }
}
