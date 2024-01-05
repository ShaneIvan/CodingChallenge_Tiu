package program;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CodingChallenge {

    public static void main(String[] args) {
        //WebDriver setup
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        //Navigate to the website
        driver.get("https://www.saucedemo.com/");
        
        //Login credentials
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        //Verify successful login and navigation to the home page
        WebElement inventoryElement = driver.findElement(By.className("inventory_item"));
        if (inventoryElement.isDisplayed()) {
            System.out.println("Login successful. User navigated to home page.");
        } 
        else {
            System.out.println("Login failed or user not on the home page.");
        }

        //Logout
        WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
        menuButton.click();

        //Wait for the logout link to be clickable with timeout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        logoutLink.click();

        //Verify user is navigated to the login page after logout
        WebElement loginForm = driver.findElement(By.id("login-button"));
        if (loginForm.isDisplayed()) {
            System.out.println("Logout successful. User navigated to the login page.");
        } 
        else {
            System.out.println("Logout failed or user not on the login page.");
        }

        //Attempt login with locked_out_user
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        //Error message for locked_out_user
        WebDriverWait waitLocked = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorElementLocked = waitLocked.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        String errorMessageLocked = errorElementLocked.getText();

        if (!errorMessageLocked.isEmpty()) {
            System.out.println("Error message displayed for locked-out user: " + errorMessageLocked);
        } else {
            System.out.println("No error message displayed for locked-out user, or login unexpectedly succeeded for locked-out user.");
        }

        //Terminate browser
        driver.quit();
    }
}