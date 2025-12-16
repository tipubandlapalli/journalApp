package net.engineeringdigest.journalapp.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class Journal {
    @Id
    private String id;
    @NonNull
    private String content;
    @NonNull
    private String title;
    private LocalDateTime localDateTime;
    private Float sentiment;
}
