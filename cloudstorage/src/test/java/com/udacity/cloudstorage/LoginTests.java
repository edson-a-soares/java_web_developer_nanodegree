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
	private LoginForm loginForm;
	private SignupForm signupForm;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		loginForm 	= new LoginForm(driver);
		signupForm 	= new SignupForm(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null)
			driver.quit();
	}

	@Test
	public void loginFailure() {
		driver.get("http://localhost:" + this.port + "/login");

		loginForm.setUsername("harry.potter");
		loginForm.setPassword("gryffindor");
		loginForm.submit();

		Assertions.assertFalse(loginForm.worked());

	}

	@Test
	public void createUserAndLoginWithSuccess() {
		driver.get("http://localhost:" + this.port + "/signup");

		signupForm.setUsername("harry.potter");
		signupForm.setPassword("gryffindor");
		signupForm.setLastName("Harry");
		signupForm.setFirstName("Potter");

		signupForm.submit();
		Assertions.assertTrue(signupForm.worked());

		driver.get("http://localhost:" + this.port + "/login");

		loginForm.setUsername("harry.potter");
		loginForm.setPassword("gryffindor");
		loginForm.submit();

		Assertions.assertTrue(loginForm.worked());

	}

}
