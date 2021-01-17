package com.udacity.cloudstorage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotesTests {

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

		driver.findElement(By.id("nav-notes-tab")).click();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null)
			driver.quit();
	}

	@Test
	public void createNote() {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Creating note
		var launchDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-note-button")));
		launchDialogButton.click();

		var noteTitleInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		noteTitleInput.clear();
		noteTitleInput.sendKeys("Selenium new note.");

		var noteDescriptionInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description")));
		noteDescriptionInput.clear();
		noteDescriptionInput.sendKeys("This is just an exercise.");

		var saveNoteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-note")));
		saveNoteButton.click();

		var responseDialog = wait.until(ExpectedConditions.elementToBeClickable(By.id("alertModalBody")));
		Assertions.assertEquals(responseDialog.getText(), "Your changes were successfully saved.");

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();

		// Verify if the new note is listed.
		var found = wait.until(ExpectedConditions.textToBe(
			By.xpath("//*[@id='userTable']/tbody/tr/th"),
			"Selenium new note.")
		);

		Assertions.assertTrue(found);
	}

	@Test
	public void editNote() {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Editing note
		var notesTable = driver.findElement(By.xpath("//*[@id='userTable']/tbody"));

		var noteInitialTitle = wait.until(
			ExpectedConditions.elementToBeClickable(
				notesTable.findElement(By.tagName("th"))
						  .findElement(By.tagName("span"))
			)
		).getText();

		var editNoteButton = wait.until(
			ExpectedConditions.elementToBeClickable(
				notesTable.findElement(By.tagName("td"))
						  .findElement(By.tagName("button")))
		);

		editNoteButton.click();

		var noteTitleInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
		noteTitleInput.clear();
		noteTitleInput.sendKeys("Selenium edited...");

		var noteDescriptionInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description")));
		noteDescriptionInput.clear();
		noteDescriptionInput.sendKeys("This is just another exercise.");

		var saveNoteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("save-note")));
		saveNoteButton.click();

		var responseDialog = wait.until(ExpectedConditions.elementToBeClickable(By.id("alertModalBody")));
		Assertions.assertEquals(responseDialog.getText(), "Your changes were successfully saved.");

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();

		// Verify if note was edited.
		var found = wait.until(ExpectedConditions.textToBe(
				By.xpath("//*[@id='userTable']/tbody/tr/th/span"),
				"Selenium edited..."
			)
		);
	}

	@Test
	public void removeNote() {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		// Removing note
		var notesTable = driver.findElement(By.xpath("//*[@id='userTable']/tbody"));
		var initialNotesNumber = notesTable.findElements(By.tagName("tr")).size();

		var removeNoteButton = wait.until(
			ExpectedConditions.elementToBeClickable(
				notesTable.findElement(By.tagName("td"))
						  .findElement(By.tagName("a"))
			)
		);

		removeNoteButton.click();

		var closeResponseDialogButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("close-alert-dialog")));
		closeResponseDialogButton.click();

		// Verify if note was deleted.
		wait.until(
			ExpectedConditions.numberOfElementsToBe(
				By.xpath("//*[@id='userTable']/tbody/tr"),
				initialNotesNumber - 1
			)
		);
	}

}
