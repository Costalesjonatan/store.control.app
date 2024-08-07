package com.stock.control.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads(ApplicationContext context) {
		then(context).isNotNull();
	}

	@Test
	public void applicationContextTest() {
		Application.main(new String[] {});
	}
}
