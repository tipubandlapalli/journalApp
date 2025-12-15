package net.engineeringdigest.journalapp.dto;

import lombok.*;
import net.engineeringdigest.journalapp.entity.Journal;

@Getter
@Setter
@Builder
public class JournalRequest {
    private final String title;
    private final String content;

    public Journal convert(){
        Journal journal = new Journal();
        if(content != null) journal.setContent(content);
        if(title != null) journal.setTitle(title);
        return journal;
    }
}
