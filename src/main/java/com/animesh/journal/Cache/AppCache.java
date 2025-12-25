package com.animesh.journal.Cache;

import com.animesh.journal.Entity.ConfigJournalApp;
import com.animesh.journal.repositry.ConfigJournalAppRepositry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {


    @Autowired
    ConfigJournalAppRepositry  configJournalAppRepositry;

    public Map<String,String> APPCACHE=new HashMap<>();

    @PostConstruct //when bean will be created(during startup of app) this method will be called automatically
    public void init(){
        List<ConfigJournalApp> all=configJournalAppRepositry.findAll();
        for(ConfigJournalApp configJournalApp:all) {
            APPCACHE.put(configJournalApp.getKey(), configJournalApp.getValue());
        }
    }
}
