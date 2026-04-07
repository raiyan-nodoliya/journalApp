package com.ngineeringdigest.journalApp.Repository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.ngineeringdigest.journalApp.Entity.User;

public interface UserRepository extends MongoRepository<User,ObjectId> {
	
	User findByUsername(String username);
	
	void deleteByUsername(String username);

}
