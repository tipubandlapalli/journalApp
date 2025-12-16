package net.engineeringdigest.journalapp.dto;

import lombok.*;
import net.engineeringdigest.journalapp.entity.Journal;

@Getter
@Setter
@Builder
public class JournalRequest {
    private final String title;
    private final String content;
    private final Float sentiment;

    public Journal convert(){
        Journal journal = new Journal();
        if(content != null) journal.setContent(content);
        if(title != null) journal.setTitle(title);
        if(sentiment != null) {
            journal.setSentiment(sentiment);
        } else {
            journal.setSentiment(5.0f);
        }
        return journal;
    }
}
