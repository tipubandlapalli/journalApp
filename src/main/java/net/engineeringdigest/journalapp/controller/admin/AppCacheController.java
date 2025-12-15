package net.engineeringdigest.journalapp.controller.admin;

import net.engineeringdigest.journalapp.cache.AppCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/config")
public class AppCacheController {

    private final AppCache appCache;

    public AppCacheController(AppCache appCache) {
        this.appCache = appCache;
    }

    @PutMapping("reset")
    public ResponseEntity<Void> init(){
        if(appCache.init()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
