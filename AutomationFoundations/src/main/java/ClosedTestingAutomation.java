import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class ClosedTestingAutomation {

    public static void main(String[] args) throws InterruptedException {
        AppiumDriver<MobileElement> driver = null;

        try {
            // Set the desired capabilities
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("deviceName", "YourDeviceName"); // Replace with your device name or ID
            caps.setCapability("udid", "YourDeviceUDID"); // Replace with the actual device UDID
            caps.setCapability("platformName", "Android");
            caps.setCapability("platformVersion", "11"); // Replace with your Android version
            caps.setCapability("appPackage", "com.example.yourapp"); // Replace with your app's package name
            caps.setCapability("appActivity", "com.example.yourapp.MainActivity"); // Replace with your app's main activity
            caps.setCapability("noReset", true); // No reset to avoid reinstalling the app

            // Initialize the Appium driver (Android)
            driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);

            // Wait for the app to load
            Thread.sleep(5000);

            // Find a clickable element by its ID and perform a click (Replace with your app's element ID)
            MobileElement element = driver.findElementById("com.example.yourapp:id/buttonId"); // Replace with your element's ID
            element.click();

            // Automate more actions as needed...
            MobileElement anotherElement = driver.findElementByXPath("//android.widget.TextView[@text='ClickMe']");
            anotherElement.click();

            // Close the app
            driver.quit();

        } catch (MalformedURLException e) {
            System.out.println("Invalid Appium Server URL");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}