package SwagLabs_Automation_Testing;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

@Listeners(Reports.class)

public class login {

    WebDriver driver;

    @BeforeMethod
    public void beforeMethod() {
        driver = new ChromeDriver();
        driver.navigate().to("https://www.saucedemo.com/");
    }

    // TC-01 | Positive: Valid credentials → redirect to Products page
    @Test
    public void testCase1_ValidLogin_RedirectsToInventory() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.cssSelector("[data-test='username']"));
        WebElement passwordField = driver.findElement(By.cssSelector("[data-test='password']"));
        WebElement loginButton = driver.findElement(By.cssSelector("[data-test='login-button']"));

        usernameField.clear();
        usernameField.sendKeys("standard_user");
        passwordField.clear();
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        Thread.sleep(1000);
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("/inventory.html"),
                "Expected URL to contain '/inventory.html' after valid login, but was: " + currentUrl);
    }

    @Test
    public void testCase2_LockedOutUser_ShowsLockedError() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.cssSelector("[data-test='username']"));
        WebElement passwordField = driver.findElement(By.cssSelector("[data-test='password']"));
        WebElement loginButton = driver.findElement(By.cssSelector("[data-test='login-button']"));

        usernameField.clear();
        usernameField.sendKeys("locked_out_user");
        passwordField.clear();
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        Thread.sleep(1000);
        WebElement errorContainer = driver.findElement(By.cssSelector("[data-test='error']"));
        String errorText = errorContainer.getText();

        Assert.assertEquals(errorText,
                "Epic sadface: Sorry, this user has been locked out.",
                "Locked-out error message is missing or incorrect.");
    }

    @Test
    public void testCase3_EmptyFields_ShowsUsernameRequiredError() throws InterruptedException {
        WebElement loginButton = driver.findElement(By.cssSelector("[data-test='login-button']"));

        Assert.assertTrue(loginButton.isEnabled(),
                "Login button should be enabled when both fields are empty.");
        loginButton.click();

        Thread.sleep(1000);
        WebElement errorContainer = driver.findElement(By.cssSelector("[data-test='error']"));
        String errorText = errorContainer.getText();

        Assert.assertEquals(errorText,
                "Epic sadface: Username is required",
                "Expected 'Username is required' error when both fields are empty.");
    }

    @Test
    public void testCase4_EmptyPassword_ShowsPasswordRequiredError() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.cssSelector("[data-test='username']"));
        WebElement passwordField = driver.findElement(By.cssSelector("[data-test='password']"));
        WebElement loginButton = driver.findElement(By.cssSelector("[data-test='login-button']"));

        usernameField.clear();
        usernameField.sendKeys("standard_user");
        passwordField.clear();
        loginButton.click();

        Thread.sleep(1000);
        WebElement errorContainer = driver.findElement(By.cssSelector("[data-test='error']"));
        String errorText = errorContainer.getText();

        Assert.assertEquals(errorText,
                "Epic sadface: Password is required",
                "Expected 'Password is required' error when only the username is filled.");
    }

    @Test
    public void testCase5_EnterKeySubmit_RedirectsToInventory() throws InterruptedException {
        WebElement usernameField = driver.findElement(By.cssSelector("[data-test='username']"));
        WebElement passwordField = driver.findElement(By.cssSelector("[data-test='password']"));

        usernameField.clear();
        usernameField.sendKeys("standard_user");
        passwordField.clear();
        passwordField.sendKeys("secret_sauce");
        passwordField.sendKeys(Keys.ENTER);

        Thread.sleep(1000);
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("/inventory.html"),
                "Expected redirect to '/inventory.html' after Enter-key submission, but was: " + currentUrl);
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }
}