package net.engineeringdigest.journalapp.cache;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalapp.repository.AppCacheRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.*;

@Service
@Slf4j
@Getter
public class AppCache {
    private Map<String , String> appCacheMap;
    private final AppCacheRepository appCacheRepository;

    public AppCache(AppCacheRepository appCacheRepository){
        this.appCacheRepository = appCacheRepository;
    }
    /**
    * this will make it to call at start
     *  and also when we call
     */
    @PostConstruct
    public boolean init(){
        try {
            Map<String, String> temp = new HashMap<>();
                appCacheRepository.findAll().forEach(
                        pair -> temp.put(pair.getKey(), pair.getValue())
                );
            appCacheMap = temp;
            log.info("cache init successfully performed");
            return true;
        } catch (Exception e){
            log.error(e.getMessage());
            return false;
        }

    }
}
