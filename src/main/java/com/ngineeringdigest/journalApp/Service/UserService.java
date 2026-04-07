package com.ngineeringdigest.journalApp.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.ngineeringdigest.journalApp.Entity.User;
import com.ngineeringdigest.journalApp.Repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
  

    public boolean saveNewEntry(User user) {
    	try {
		        user.setPassword(passwordEncoder.encode(user.getPassword()));
		        user.setRoles(Arrays.asList("USER")); 
		        userRepository.save(user);
		        return true;
    	}catch(Exception e){
    		
			log.error("error occured for :{}",user.getUsername());
			log.debug("error occured for :{}",user.getUsername());
			log.warn("error occured for :{}",user.getUsername());
			log.trace("error occured for :{}",user.getUsername());
    		return false;
    	}
    }
    
    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN")); 
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteByid(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }
    
    
}