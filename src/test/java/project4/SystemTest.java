package project4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gatechprojects.project4.BAL.UserService;
import org.junit.Test;

public class SystemTest {
	static final Logger LOG = LogManager.getLogger(SystemTest.class.getName());

	@Test
	public void logSomething() {
		LOG.info("Testing log, This test should be removed");
	}

	@Test
	public void userTest() {
		UserService userService = new UserService();
		int userId = userService.addUser("Andrew", "Freeze", true, false, false);
		userService.updateUser(userId, "Andrew", "Frieze", true, false, false);
	}
}
