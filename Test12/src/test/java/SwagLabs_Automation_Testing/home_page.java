package SwagLabs_Automation_Testing;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

@Listeners(Reports.class)

public class home_page {

    WebDriver d;

    @BeforeMethod
    public void start() {

        System.setProperty("webdriver.chrome.driver", "E:\\Testing\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

        d = new ChromeDriver();

        d.navigate().to("https://www.saucedemo.com/");
    }

    public void login() {

        WebElement usernameField = d.findElement(By.id("user-name"));
        usernameField.sendKeys("standard_user");

        WebElement passwordField = d.findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");

        WebElement loginButton = d.findElement(By.id("login-button"));
        loginButton.click();
    }

    public WebElement product(String name) {

        WebElement item = d.findElement(By.xpath("//div[@class='inventory_item' and .//div[text()='" + name + "']]"));
        return item;
    }

    public void openMenu() {

        WebDriverWait wait = new WebDriverWait(d, Duration.ofSeconds(15));

        WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
        menuButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
    }

    @Test
    public void validLogin() {

        login();

        WebElement inventoryContainer = d.findElement(By.className("inventory_container"));
        Assert.assertTrue(inventoryContainer.isDisplayed());

        WebElement pageTitle = d.findElement(By.className("title"));
        Assert.assertEquals(pageTitle.getText(), "Products");

        WebElement appLogo = d.findElement(By.className("app_logo"));
        Assert.assertEquals(appLogo.getText(), "Swag Labs");
    }

    @Test
    public void sorting() {

        login();

        WebElement sortDropdown = d.findElement(By.className("product_sort_container"));
        Select sort = new Select(sortDropdown);
        sort.selectByValue("lohi");

        WebElement onesieCard = product("Sauce Labs Onesie");
        WebElement onesieName = onesieCard.findElement(By.className("inventory_item_name"));

        String actual = onesieName.getText();
        String expected = "Sauce Labs Onesie";

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void openProduct() {

        login();

        WebElement bikeLightCard = product("Sauce Labs Bike Light");
        WebElement bikeLightName = bikeLightCard.findElement(By.className("inventory_item_name"));
        bikeLightName.click();

        WebElement detailsName = d.findElement(By.className("inventory_details_name"));

        String actual = detailsName.getText();
        String expected = "Sauce Labs Bike Light";

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void addToCart() {

        login();

        WebElement addButton = d.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addButton.click();

        WebElement removeButton = d.findElement(By.id("remove-sauce-labs-bike-light"));

        String actual = removeButton.getText();
        String expected = "Remove";

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void removeFromCart() {

        login();

        WebElement addButton = d.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addButton.click();

        WebElement removeButton = d.findElement(By.id("remove-sauce-labs-bike-light"));
        removeButton.click();

        WebElement addButtonAfterRemove = d.findElement(By.id("add-to-cart-sauce-labs-bike-light"));

        String actual = addButtonAfterRemove.getText();
        String expected = "Add to cart";

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void Logout() {

        login();

        openMenu();

        WebElement logoutLink = d.findElement(By.id("logout_sidebar_link"));
        logoutLink.click();

        WebElement loginButton = d.findElement(By.id("login-button"));
        Assert.assertTrue(loginButton.isDisplayed());
    }

    @AfterMethod
    public void close() {

        d.quit();
    }
}