package com.animesh.journal.Scheduler;

import com.animesh.journal.Cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class AppCacheScheduler {

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 */5 * * * *" )
    public void clearAppCache(){
        appCache.init();
    }
}
