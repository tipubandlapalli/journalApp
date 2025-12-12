package net.engineeringdigest.journalApp.entity;


import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "journal_entries")
@Data
public class Journal {
    @Id
    private ObjectId id;
    @NonNull
    private String content;
    @NonNull
    private String title;
    private LocalDateTime localDateTime;
}
