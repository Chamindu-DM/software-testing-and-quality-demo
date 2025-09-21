package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object Model for the Login Page.
 * Encapsulates the elements and actions on the login page.
 *
 */
public class LoginPage {
    private WebDriver driver;

    // Locators
    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(className = "error-message")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // Initialize elements
        PageFactory.initElements(driver, this);
    }

    /**
     * Performs a login action.
     *
     * @param username The username to enter.
     * @param password The password to enter.
     */
    public void login(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    /**
     * Checks if the error message is displayed.
     *
     * @return true if the error message is visible, false otherwise.
     */
    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();
    }
}
