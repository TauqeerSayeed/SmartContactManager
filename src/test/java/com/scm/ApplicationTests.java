package com.scm;

import com.scm.Services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService service;

	@Test
	void sendEmailTest(){
		service.sendEmail("khanmaeinuddin19@gmail.com",
				"just testing Email",
				"this is scm project testing working on email Services"
				);
	}
}
