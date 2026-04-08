package com.ngineeringdigest.journalApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngineeringdigest.journalApp.Cache.AppCache;
import com.ngineeringdigest.journalApp.Entity.User;
import com.ngineeringdigest.journalApp.Service.UserService;
import com.ngineeringdigest.journalApp.Utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AppCache appCache;
	
	@GetMapping("/health-check")
	public String healthCheck() {
		return "OK";
	}
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody User user) {
		try {
			userService.saveNewEntry(user);
			return new ResponseEntity<>(user,HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		try {
			 authenticationManager.authenticate(
					 new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		    UserDetails userDetails= userDetailsService.loadUserByUsername(user.getUsername());
		    String jwt= jwtUtil.generateToken(userDetails.getUsername());
		    return new ResponseEntity<>(jwt,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error("Exception occued while createAuthenticationToken",e);
			return new ResponseEntity<>("Incorrect username or password ",HttpStatus.BAD_REQUEST);
		}
	}
	
//	@GetMapping("clear-app-cache")
//	public void clearAppCache() {
//		appCache.init();
//	}


}
