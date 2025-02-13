import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PlaySpiralMountainTuneAutomation {

    public static void main(String[] args) throws InterruptedException {
        // Set the MOZ_REMOTE_SETTINGS_DEVTOOLS environment variable
        System.setProperty("MOZ_REMOTE_SETTINGS_DEVTOOLS", "1");

        // Set the path to the Gecko Firefox executable
        System.setProperty("webdriver.gecko.driver", "/Users/michaellaverty/Desktop/geckodriver");

        // Create FirefoxOptions and add arguments to disable notifications
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("app.update.auto", false);

        // Create a new instance of FirefoxDriver
        WebDriver driver = new FirefoxDriver();


        // Navigate to Smash Bros website
        driver.get("https://www.smashbros.com/en_GB/sound/index.html");

        // Scroll down using JavaScript
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0, 400);"); // Adjust the scrolling distance as needed

        WebDriverWait wait = new WebDriverWait(driver, 2);

        WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/div[2]/div/form/div/div[1]/div/div[2]/div/div/div[1]/button/div/span")));
        acceptButton.click();

        WebElement playTheme = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div[3]/div[2]/div[1]/div[1]/div/ul/li[1]/a/div[5]/div[3]/i[1]"));

        playTheme.click();
        playTheme.wait();

        // Close the browser
        driver.quit();
    }

}