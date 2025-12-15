package net.engineeringdigest.journalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.engineeringdigest.journalapp.entity.Journal;

@Getter
@Setter
@NoArgsConstructor
public class JournalRequest {
    private String title;
    private String content;

    public Journal convert(){
        Journal journal = new Journal();
        if(content != null) journal.setContent(content);
        if(title != null) journal.setTitle(title);
        return journal;
    }
}
