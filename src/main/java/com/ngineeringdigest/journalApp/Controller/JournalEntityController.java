package com.ngineeringdigest.journalApp.Controller;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ngineeringdigest.journalApp.Entity.JournalEntry;
import com.ngineeringdigest.journalApp.Entity.User;
import com.ngineeringdigest.journalApp.Service.JournalEntryService;
import com.ngineeringdigest.journalApp.Service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntityController {

	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;
	

	@GetMapping
	public ResponseEntity<?> getAllJournalEntriesOfUser()
	{
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userService.findByUserName(username);
		
		List<JournalEntry> all = user.getJournalEntries();
		if(all !=null && !all.isEmpty()) 
		{
			return new ResponseEntity<>(all,HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
		try {
			Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			journalEntryService.saveEntry(myEntry,username);
			
			return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myId) {
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user=userService.findByUserName(username);
		
		List<JournalEntry> collect= user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
		
		if(!collect.isEmpty()) {
			 Optional<JournalEntry> journalEntry=journalEntryService.finfdById(myId);
			 if(journalEntry.isPresent()) {
			    	return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
			    }
		}
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	
	@DeleteMapping("/id/{myId}")
	public ResponseEntity<?>  deleteJournalById(@PathVariable ObjectId myId) {
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
	    boolean removed =journalEntryService.deleteByid(myId,username);
	    if(removed) {
	    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }else {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    
	}
	
	
	@PutMapping("/id/{myId}")
	public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, 
			@RequestBody JournalEntry newEntry
			
	) {
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user=userService.findByUserName(username);
		List<JournalEntry> collect= user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
		if(!collect.isEmpty()) {
			 Optional<JournalEntry> journalEntry=journalEntryService.finfdById(myId);
			 if(journalEntry.isPresent()) {
				 	JournalEntry old = journalEntry.get();
				 	old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals(" ") ? newEntry.getTitle() : old.getTitle());
					old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals(" ") ? newEntry.getContent() : old.getContent());
					journalEntryService.saveEntry(old);
					return new ResponseEntity<>(old,HttpStatus.OK);
			    }
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		
		
			
		
	}
}
