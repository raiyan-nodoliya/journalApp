package com.ngineeringdigest.journalApp.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ngineeringdigest.journalApp.Entity.JournalEntry;
import com.ngineeringdigest.journalApp.Entity.User;
import com.ngineeringdigest.journalApp.Repository.JournalEntryRepository;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class JournalEntryService {

	@Autowired
	private  JournalEntryRepository journalEntryRepository;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

   
	
	@Transactional
	public void saveEntry(JournalEntry journalEntry, String username) {
		try {
			User user= userService.findByUserName(username);
			journalEntry.setDate(LocalDateTime.now());
			JournalEntry saved = journalEntryRepository.save(journalEntry);
			user.getJournalEntries().add(saved);
			userService.saveUser(user);
			
		}catch (Exception e) {
			
			throw new RuntimeException("An Error occured while saving the entry");
		}
		
	}
	
	

	public void saveEntry(JournalEntry journalEntry) {
		journalEntryRepository.save(journalEntry);
	}
	
	
	public List<JournalEntry> getAll(){
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> finfdById(ObjectId id) {
		return journalEntryRepository.findById(id);
	}
	
	
	@Transactional
	public boolean deleteByid(ObjectId id, String username) {
		boolean removed = false;
		try {
			
			User user= userService.findByUserName(username);
			removed =user.getJournalEntries().removeIf(x -> x.getId().equals(id));
			if(removed) {
				userService.saveUser(user);
				journalEntryRepository.deleteById(id);
			}
			
		}catch(Exception e) {
			System.out.println(e);
			throw new RuntimeException("An Error occured while deleting the entry",e);
		}
		
		return removed;
		
		
	}
	
	

}



