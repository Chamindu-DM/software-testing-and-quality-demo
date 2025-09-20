package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object Model for the Dashboard/Task Management Page.
 *
 * @author Gemini
 * @version 1.0
 */
public class DashboardPage {
    private WebDriver driver;

    // Locators
    @FindBy(id = "task-title-input")
    private WebElement taskTitleInput;

    @FindBy(id = "task-description-input")
    private WebElement taskDescriptionInput;

    @FindBy(id = "add-task-button")
    private WebElement addTaskButton;

    @FindBy(className = "task-list")
    private WebElement taskList;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Adds a new task from the UI.
     *
     * @param title The title of the new task.
     * @param description The description of the new task.
     */
    public void addTask(String title, String description) {
        taskTitleInput.sendKeys(title);
        taskDescriptionInput.sendKeys(description);
        addTaskButton.click();
    }

    /**
     * Checks if a specific task is visible in the list.
     * In a real app, this would check for an element with the task title.
     *
     * @param title The title of the task to find.
     * @return true if the task is found, false otherwise.
     */
    public boolean isTaskVisible(String title) {
        return taskList.getText().contains(title);
    }
}
