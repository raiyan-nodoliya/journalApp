package com.ngineeringdigest.journalApp.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ngineeringdigest.journalApp.Repository.UserRepository;

@SpringBootTest
public class UserServiceTests {

	@Autowired
	private UserRepository userRepository;
	
	@Disabled
	@Test
	public void testFindByUsername() {
		
		assertNotNull(userRepository.findByUsername("raiyan"));
		assertTrue(5 > 3);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1,1,2",
		"3,4,7",
		"3,3,6"
	})
	public void test(int a,int b, int expected) {
		assertEquals(expected, a+b);
	}
}
