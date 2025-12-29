package com.animesh.journal.services;

import com.animesh.journal.enums.Sentiment;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SentimentAnalysisService {

    public String  sentimentAnalysis(List<Sentiment> data){
        Map<Sentiment,Integer> sentimentcount=new HashMap<>();
        for(Sentiment sentiment : data){
            if(sentiment!=null){
                sentimentcount.put(sentiment,sentimentcount.get(sentiment)+1);
            }
        }
        Sentiment maxFrequentSentiment=null;
        int maxCount=0;
        for(Map.Entry<Sentiment,Integer> entry : sentimentcount.entrySet()){
            if(entry.getValue()>maxCount){
                maxCount=entry.getValue();
                maxFrequentSentiment=entry.getKey();
            }
        }
        if(maxFrequentSentiment!=null){
            return maxFrequentSentiment.toString();
        }
        return "You have not mentioned any sentiment on your Journals";
    }
}
