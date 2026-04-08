package com.ngineeringdigest.journalApp.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
	
	@Autowired
	private EmailService emailService;
	

//	@Disabled
//	@Test
//	public void testSendMail() {
//		emailService.sendEmail("nodoliyaraiyan9712@gmail.com", "Testing Java mail sender", "Hii aap kayse he ");
//	}

}
