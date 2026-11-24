package SwagLabs_Automation_Testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Reports.class)

public class Logout {

    WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "E:\\Testing\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        Thread.sleep(2000);

        driver.findElement(By.cssSelector("[data-test='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("[data-test='password']")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("[data-test='login-button']")).click();

        Thread.sleep(2000);
    }

    @Test
    public void testCase1() throws InterruptedException {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("[data-test='logout-sidebar-link']")).click();
        Thread.sleep(2000);

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/",
                "URL should change to the Login page URL after logout");
    }

    @Test
    public void testCase2() throws InterruptedException {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("[data-test='logout-sidebar-link']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.findElement(By.cssSelector("[data-test='login-button']"))
                .isDisplayed(), "Login button should be displayed after logout");
    }

    @Test
    public void testCase3() throws InterruptedException {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("[data-test='logout-sidebar-link']")).click();
        Thread.sleep(2000);

        Assert.assertEquals(driver.findElements(By.cssSelector("[data-test='inventory-container']"))
                .size(), 0, "Products page content should not be visible after logout");
    }

    @Test
    public void testCase4() throws InterruptedException {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.findElement(By.cssSelector("[data-test='logout-sidebar-link']"))
                .isDisplayed(), "Logout option should be visible in the side menu");
    }

    @Test
    public void testCase5() throws InterruptedException {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(2000);

        driver.findElement(By.cssSelector("[data-test='logout-sidebar-link']")).click();
        Thread.sleep(2000);

        driver.navigate().back();
        Thread.sleep(2000);

        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "Products page must NOT be accessible after logout using the Back button");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}