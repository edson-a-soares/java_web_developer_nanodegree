package com.udacity.cloudstorage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginTests {

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
		login 		= new LoginForm(driver);
		signup 		= new SignupForm(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null)
			driver.quit();
	}

	private boolean tryLogin(String username, String password) {
		driver.get("http://localhost:" + this.port + "/login");

		login.setUsername(username);
		login.setPassword(password);
		login.submit();

		return login.success();
	}

	private boolean tryCreateUser(String firstName, String lastName, String username, String password) {
		driver.get("http://localhost:" + this.port + "/signup");

		signup.setUsername(username);
		signup.setPassword(password);
		signup.setLastName(firstName);
		signup.setFirstName(lastName);
		signup.submit();

		return signup.success();
	}

	@Test
	public void nonexistentUserLoginFailure() {
		Assertions.assertFalse(tryLogin("harry.potter", "gryffindor"));
	}

	@Test
	public void loginWithSuccess() {
		Assertions.assertTrue(
			tryCreateUser(
			"Harry",
			"Potter",
			"harry.potter",
			"gryffindor"
			)
		);

		Assertions.assertTrue(tryLogin("harry.potter", "gryffindor"));
		Assertions.assertEquals("Home", driver.getTitle());
	}

}
