package project4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SystemTest {
	static final Logger LOG = LogManager.getLogger(SystemTest.class.getName());

	@Test
	public void logSomething() {
		LOG.info("Testing log, This test should be removed");
	}
}
