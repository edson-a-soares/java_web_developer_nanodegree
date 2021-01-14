package com.udacity.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginForm {

    @FindBy(tagName = "title")
    private WebElement pageTitle;

    @FindBy(id = "login-form-container")
    private WebElement formContainer;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "login-submit-button")
    private WebElement submitButton;

    public LoginForm(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void submit() {
        submitButton.click();
    }

    public void setUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public boolean worked() {
        return !pageTitle.getText().equals("Login") && !hasErrorMessage();
    }

    private boolean hasErrorMessage() {
        boolean result = false;
        try {
            result = formContainer.findElements(By.id("invalid-credentials-message")).size() != 0;
        } catch (Exception ignored) {}

        return result;
    }

}