package com.udacity.cloudstorage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialsTests {

	@LocalServerPort
	private int port;
	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		var login = new LoginForm(driver);

		driver.get("http://localhost:" + this.port + "/login");
		login.setUsername("default");
		login.setPassword("abc123");
		login.submit();

		Assertions.assertTrue(login.success());

		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleIs("Home"));

		driver.findElement(By.id("nav-credentials-tab")).click();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null)
			driver.quit();
	}

	@Test
	public void createCredential() {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Creating credential
		var launchDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-credential-button")));
		launchDialogButton.click();

		var credentialUrlInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
		credentialUrlInput.clear();
		credentialUrlInput.sendKeys("https://gmail.com");

		var credentialUsernameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username")));
		credentialUsernameInput.clear();
		credentialUsernameInput.sendKeys("default");

		var credentialPasswordInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
		credentialPasswordInput.clear();
		credentialPasswordInput.sendKeys("abc123456");

		var saveCredentialButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-credential")));
		saveCredentialButton.click();

		var responseDialog = wait.until(ExpectedConditions.elementToBeClickable(By.id("alertModalBody")));
		Assertions.assertEquals(responseDialog.getText(), "Your changes were successfully saved.");

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();

		// Verify if the new credential is listed.
		var found = wait.until(ExpectedConditions.textToBe(
			By.xpath("//*[@id='credentialTable']/tbody/tr/th"),
			"https://gmail.com")
		);

		Assertions.assertTrue(found);

		// Verify if the new credential is encrypted.
		var passwordColumn = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[3]"));
		wait.until(ExpectedConditions.visibilityOf(passwordColumn));

		Assertions.assertNotSame("abc123456", passwordColumn.getText());
	}

	@Test
	public void editCredential() {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Editing credential
		var notesTable = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody"));

		var credentialInitialPassword = wait.until(
			ExpectedConditions.elementToBeClickable(
				notesTable.findElement(By.tagName("th"))
						  .findElement(By.tagName("span"))
			)
		).getText();

		var editCredentialButton = wait.until(
			ExpectedConditions.elementToBeClickable(
				notesTable.findElement(By.tagName("td"))
						  .findElement(By.tagName("button")))
		);

		editCredentialButton.click();

		var credentialUrlInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
		credentialUrlInput.clear();
		credentialUrlInput.sendKeys("https://gmail.com");

		var credentialUsernameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username")));
		credentialUsernameInput.clear();
		credentialUsernameInput.sendKeys("something");

		var credentialPasswordInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
		credentialPasswordInput.clear();
		credentialPasswordInput.sendKeys("abc123456");

		var saveCredentialButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-credential")));
		saveCredentialButton.click();

		var responseDialog = wait.until(ExpectedConditions.elementToBeClickable(By.id("alertModalBody")));
		Assertions.assertEquals(responseDialog.getText(), "Your changes were successfully saved.");

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();

		// Verify if the new credential is edited.
		var found = wait.until(ExpectedConditions.textToBe(
				By.xpath("//*[@id='credentialTable']/tbody/tr/th"),
				"https://gmail.com")
		);

		Assertions.assertTrue(found);

		// Verify if the new credential is encrypted.
		var passwordColumn = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[3]"));
		wait.until(ExpectedConditions.visibilityOf(passwordColumn));

		Assertions.assertNotSame(credentialInitialPassword, passwordColumn.getText());
		Assertions.assertNotSame("abc123456", passwordColumn.getText());
	}

	@Test
	public void removeCredential() {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Removing note
		var credentialsTable = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody"));
		var initialNotesNumber = credentialsTable.findElements(By.tagName("tr")).size();

		var removeCredentialButton = wait.until(
			ExpectedConditions.elementToBeClickable(
				credentialsTable
					.findElement(By.tagName("td"))
					.findElement(By.tagName("a"))
			)
		);

		removeCredentialButton.click();

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();

		// Verify if note was deleted.
		wait.until(
			ExpectedConditions.numberOfElementsToBe(
				By.xpath("//*[@id='credentialTable']/tbody/tr"),
				initialNotesNumber - 1
			)
		);
	}

}
