package com.udacity.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogoutTests {

	@LocalServerPort
	private int port;
	private WebDriver driver;
	private LoginForm login;
	private SignupForm signup;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		login = new LoginForm(driver);
		signup = new SignupForm(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null)
			driver.quit();
	}

	@Test
	public void logoutWithSuccess() {
		driver.get("http://localhost:" + this.port + "/signup");

		signup.setUsername("harry.potter");
		signup.setPassword("gryffindor");
		signup.setLastName("Harry");
		signup.setFirstName("Potter");

		signup.submit();
		Assertions.assertTrue(signup.success());

		driver.get("http://localhost:" + this.port + "/login");

		login.setUsername("harry.potter");
		login.setPassword("gryffindor");
		login.submit();

		Assertions.assertTrue(login.success());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/logout");
		Assertions.assertNotSame("Home", driver.getTitle());
	}

}
