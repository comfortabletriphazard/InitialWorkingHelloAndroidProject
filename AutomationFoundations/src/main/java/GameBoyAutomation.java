import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GameBoyAutomation {

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

        // Navigate to the URL
        driver.get("https://vimm.net/vault/2637");

        // Decline cookies prompt
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/div/div[2]/div/button[2]")));
            acceptButton.click();
        } catch (Exception e) {
            // Cookies dialog did not appear or not needed
        }

        scrollDownSlowly(driver);

        // Replace "Click Me" with the text you want to click
        String targetText = "Play Online";

        // Find the element using the link text
        WebElement element = driver.findElement(By.id("Play Online"));
        element.click();


//        // Click "Play Online"
//        WebElement playOnlineButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[5]/div[2]/div/div[3]/div[2]/div[1]/table/tbody/tr[19]/td/div[2]/form/button")));
//        playOnlineButton.click();

//        // Click "Play Now"
//        try {
//            WebElement playNowButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ejs-start-game\"]")));
//            playNowButton.click();
//        } catch (Exception e) {
//            // Cookies dialog did not appear or not needed
//        }

        // Close the browser
//        driver.quit();
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

