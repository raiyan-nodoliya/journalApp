package com.ngineeringdigest.journalApp.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ngineeringdigest.journalApp.Entity.User;

@Repository
public class UserRepositoryImp {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA() {

        Query query = new Query();

        query.addCriteria(Criteria.where("email")
                .regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));

        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        query.addCriteria(Criteria.where("roles").in("USER", "ADMIN"));

        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}