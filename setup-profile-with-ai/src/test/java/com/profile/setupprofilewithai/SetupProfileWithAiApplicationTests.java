package com.profile.setupprofilewithai;

import com.profile.setupprofilewithai.service.ChatGptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class SetupProfileWithAiApplicationTests {

	@Mock
	private ChatGptService chatGptService;

	@Test
	void contextLoads() {
	}

}
