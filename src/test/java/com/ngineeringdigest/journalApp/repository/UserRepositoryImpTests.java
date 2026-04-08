package com.ngineeringdigest.journalApp.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ngineeringdigest.journalApp.Repository.UserRepositoryImp;

@SpringBootTest
public class UserRepositoryImpTests {
	
	private UserRepositoryImp userRepositoryImp;
	
	@Disabled
	@Test
	public void testSaveNewUser() {
		userRepositoryImp.getUserForSA();
	}

}
