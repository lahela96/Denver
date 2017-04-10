package com.colorado.denver.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaExecutionTest {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JavaExecutionTest.class);

	@Test
	public void executeJava() {
		LOGGER.info("Start of bytebuddy");
		// Use nashorn for js

		LOGGER.info("End of bytebuddy");
	}
}
