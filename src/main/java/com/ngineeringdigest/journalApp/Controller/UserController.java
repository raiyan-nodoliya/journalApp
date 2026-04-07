package com.ngineeringdigest.journalApp.Controller;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngineeringdigest.journalApp.Entity.User;
import com.ngineeringdigest.journalApp.Repository.UserRepository;
import com.ngineeringdigest.journalApp.Service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private UserRepository userRepository;
	
	

	@GetMapping
	public ResponseEntity<?> getAll() {
	    List<User> all = userService.getAll();
	    if(all != null && !all.isEmpty()) {
	        return new ResponseEntity<>(all, HttpStatus.OK);
	    }
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	
	
	@GetMapping("/id/{myId}")
	public ResponseEntity<User> getUserById(@PathVariable ObjectId myId) {
	    Optional<User> userEntry=userService.findById(myId);
	    
	    if(userEntry.isPresent()) {
	    	return new ResponseEntity<>(userEntry.get(),HttpStatus.OK);
	    }
	    
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	
	@DeleteMapping
	public ResponseEntity<?> deleteUserById() {

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    userRepository.deleteByUsername(authentication.getName());

	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		User userInDb = userService.findByUserName(username);
			userInDb.setUsername(user.getUsername());
			userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.saveUser(userInDb);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
			
	}
	
}
