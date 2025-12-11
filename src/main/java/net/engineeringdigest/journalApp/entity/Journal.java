package net.engineeringdigest.journalApp.entity;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.Date;


@Document(collection = "journal_entries")
@Data
public class Journal {
    @Id
    private ObjectId id;
    private String content;
    private String title;
    private Date date;
}
