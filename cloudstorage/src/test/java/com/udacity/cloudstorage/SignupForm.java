package com.udacity.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupForm {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "signup-submit-button")
    private WebElement submitButton;

    @FindBy(id = "signup-form-container")
    private WebElement formContainer;

    public SignupForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void submit() {
        submitButton.click();
    }

    public void setFirstName(String name) {
        firstNameField.clear();
        firstNameField.sendKeys(name);
    }

    public void setLastName(String name) {
        lastNameField.clear();
        lastNameField.sendKeys(name);
    }

    public void setUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public boolean success() {
        return !hasErrorMessage();
    }

    private boolean hasErrorMessage() {
        boolean result = false;
        try {
            result = formContainer.findElements(By.id("signup-error-message")).size() != 0;

        } catch (Exception ignored) {}

        return result;
    }

}