import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WordleAutomator {
    public static void main(String[] args) {
        // Set the path to the Gecko Firefox executable
        System.setProperty("webdriver.gecko.driver", "/Users/michaellaverty/Desktop/geckodriver");

        // Create FirefoxOptions and add arguments to disable notifications
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("app.update.auto", false);

        // Create a new instance of FirefoxDriver
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
            driver.get("https://www.nytimes.com/games/wordle/index.html");

            // Click the "Continue" button if it's displayed
            WebElement continueButton = driver.findElement(By.className("purr-blocker-card__button"));
            if (continueButton.isDisplayed()) {
                continueButton.click();
            }

            // Click the "Accept" button if it's displayed
            WebElement acceptButton = driver.findElement(By.xpath("//button[contains(text(), 'REJECT')]"));
            if (acceptButton.isDisplayed()) {
                acceptButton.click();
            }

            // Click the Play button
            WebElement playButton = driver.findElement(By.xpath("//button[contains(text(), 'Play')]"));
            playButton.click();

            WebElement howToPlayDialog = driver.findElement(By.xpath("//button[contains(text(), 'Play')]"));

            // Dismiss the dialog pop-up
            WebElement dialogCloseButton = driver.findElement(By.className("game-icon"));
            dialogCloseButton.click();

            //Scroll down so grid appears
            scrollDownSlowly(driver);

            // Find an element where you want to send the keyboard letter press
            WebElement element = driver.findElement(By.xpath("//button[contains(class(), 'Tile-module_tile__UWEHN')]"));
            element.sendKeys("a");
            element.sendKeys("p");
            element.sendKeys("p");
            element.sendKeys("l");
            element.sendKeys("e");
            element.submit();

//            // Find the input field for the first row
//            WebElement inputField = driver.findElement(By.className("Board-module_board__jeoPS\"]/div[1]/div[1])"));
//            inputField.sendKeys("a");



//
//            //iteration on below:
//            int[][] array = new int[6][5];
//
//            // Fill the array with values from 1 to 30
//            int counter = 1;
//            for (int i = 0; i < 6; i++) {
//                for (int j = 0; j < 5; j++) {
//                    array[i][j] = counter++;
//                }
//            }
//
//            // Iterating through the array and generating class names
//            for (int i = 0; i < 6; i++) {
//                for (int j = 0; j < 5; j++) {
//                    // Find the input field for the first row
//                    WebElement inputField = driver.findElement(By.className("Board-module_board__jeoPS\"]/div[" + (i+1) +"]/div["  + (j+1) + "])"));
//                    // Simulate entering the word "apple"
//                    inputField.sendKeys("a");
//                    Thread.sleep(100); // Simulate typing delay
//                    inputField.sendKeys("p");
//                    Thread.sleep(100); // Simulate typing delay
//                    inputField.sendKeys("p");
//                    Thread.sleep(100); // Simulate typing delay
//                    inputField.sendKeys("l");
//                    Thread.sleep(100); // Simulate typing delay
//                    inputField.sendKeys("e");
//                    Thread.sleep(100); // Simulate typing delay
//                    inputField.sendKeys(Keys.ENTER);
//                    System.out.println("Class name: " + inputField);
//                    // Here, you can insert the class name into your structure or perform other operations
//                }
//            }


//            Map<String,Object> params = new HashMap<>();
//
//            params.put("label", "MILES");  //this relates to the xpath //*[@label="MILES"]
//            params.put("threshold", 80); //This is a percentage of how accurately the label MILES is, to be found, the lower it is, the more casual the attempt to find
//            params.put("ignorecase", "nocase");
//            driver.executeScript("mobile:button-text:click", params); //Finds the text
//
            
//            // Simulate entering the word "apple"
//            inputField.sendKeys("a");
//            Thread.sleep(100); // Simulate typing delay
//            inputField.sendKeys("p");
//            Thread.sleep(100); // Simulate typing delay
//            inputField.sendKeys("p");
//            Thread.sleep(100); // Simulate typing delay
//            inputField.sendKeys("l");
//            Thread.sleep(100); // Simulate typing delay
//            inputField.sendKeys("e");
//            Thread.sleep(100); // Simulate typing delay
//            inputField.sendKeys(Keys.ENTER);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
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

    // Replace this with your own word generation logic
    private static String generateWord() {
        // Generate a random word or use a predefined list
        return "apple"; // Replace with your word
    }
}