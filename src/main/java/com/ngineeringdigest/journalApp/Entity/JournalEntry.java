package com.ngineeringdigest.journalApp.Entity;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ngineeringdigest.journalApp.Enums.Sentiment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {
	
	@Id
	private ObjectId id;
	private String title;
	private String content;
	private LocalDateTime date;
	private Sentiment sentiment;
	

}
