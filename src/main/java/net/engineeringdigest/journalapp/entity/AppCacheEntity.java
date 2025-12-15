package net.engineeringdigest.journalapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@Document(collection = "app_cache")
public class AppCacheEntity {
    @Id
    private String id;
    private String key;
    private String value;
}
