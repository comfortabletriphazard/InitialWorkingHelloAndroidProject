import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ZeldaUniverseAutomation {

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

        // Open Zelda Universe
        driver.get("https://zeldauniverse.net/");

        // Handle cookies dialog if it pops up
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            WebElement allowButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("AGREE")));
            allowButton.click();
        } catch (Exception e) {
            // Cookies dialog did not appear or not needed
        }

        // Wait for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Get page title
        System.out.println(driver.getTitle() + " " + driver.getCurrentUrl());

        // Find the search bar and enter the search query
        WebElement searchBox = driver.findElement(By.id("s"));
        searchBox.sendKeys("Ireland");
        searchBox.submit();

        // Wait for search results to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scrollDownSlowly(driver);

//         Close the browser
        driver.quit();
    }

    private static void scrollDownSlowly(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        long scrollHeight = (long) js.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight );");

        long windowHeight = (long) js.executeScript("return window.innerHeight;");

        int stepSize = 100; // Adjust the step size as needed

        for (long scroll = 0; scroll < scrollHeight; scroll += stepSize) {
            js.executeScript("window.scrollBy(0, " + stepSize + ");");
            try {
                Thread.sleep(500); // Adjust the sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
