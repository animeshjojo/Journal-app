package com.animesh.journal.Cache;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppCache {

    private Map<String,String> appCache;

    @PostConstruct //when bean will be created(during startup of app) this method will be called automatically
    public void init(){
        appCache = null;
    }
}
