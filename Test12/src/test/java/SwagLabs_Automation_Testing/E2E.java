package SwagLabs_Automation_Testing;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.List;

@Listeners(Reports.class)

public class E2E {
    WebDriver b;

    @BeforeMethod
    public void pre_condition() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "E:\\Testing\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
        b = new ChromeDriver();
        b.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(5));
        b.navigate().to("https://www.saucedemo.com/");

        WebElement username = b.findElement(By.id("user-name"));
        username.sendKeys("standard_user");

        WebElement pass = b.findElement(By.name("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        Thread.sleep(1000);
    }

    @Test
    public void testcase1() throws InterruptedException {

        SoftAssert soft = new SoftAssert();

        soft.assertTrue(b.findElement(By.className("footer_copy")).isDisplayed());

        b.findElement(By.className("product_sort_container")).click();
        b.findElement(By.xpath("//option[text()='Name (Z to A)']")).click();
        Thread.sleep(500);
        soft.assertEquals(b.findElement(By.className("inventory_item_name")).getText(), "Test.allTheThings() T-Shirt (Red)");

        b.findElement(By.className("product_sort_container")).click();
        b.findElement(By.xpath("//option[text()='Price (low to high)']")).click();
        Thread.sleep(500);
        soft.assertEquals(b.findElement(By.className("inventory_item_price")).getText(), "$7.99");

        b.findElement(By.className("product_sort_container")).click();
        b.findElement(By.xpath("//option[text()='Price (high to low)']")).click();
        Thread.sleep(500);
        soft.assertEquals(b.findElement(By.className("inventory_item_price")).getText(), "$49.99");

        b.findElement(By.className("product_sort_container")).click();
        b.findElement(By.xpath("//option[text()='Name (A to Z)']")).click();
        Thread.sleep(500);
        soft.assertEquals(b.findElement(By.className("inventory_item_name")).getText(), "Sauce Labs Backpack");

        b.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        b.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        soft.assertEquals(b.findElement(By.className("shopping_cart_badge")).getText(), "2");

        b.findElement(By.id("item_1_title_link")).click();
        b.findElement(By.id("add-to-cart")).click();
        b.findElement(By.id("remove")).click();
        b.findElement(By.id("back-to-products")).click();

        b.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(500);
        b.findElement(By.id("dynamic_catalog_sidebar_link")).click();
        b.findElement(By.id("dynamic_catalog_lazy_load_link")).click();
        Thread.sleep(1000);

        int before = b.findElements(By.className("dynamic_catalog_card_name")).size();
        b.findElement(By.tagName("body")).sendKeys(Keys.END);
        Thread.sleep(1000);
        int after = b.findElements(By.className("dynamic_catalog_card_name")).size();
        soft.assertTrue(after > before, "Lazy load did not load more products");

        b.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(500);
        b.findElement(By.id("inventory_sidebar_link")).click();
        b.findElement(By.className("shopping_cart_link")).click();
        soft.assertEquals(b.findElements(By.className("cart_item")).size(), 2);

        b.findElement(By.id("remove-sauce-labs-bike-light")).click();
        b.findElement(By.id("remove-sauce-labs-backpack")).click();
        soft.assertEquals(b.findElements(By.className("cart_item")).size(), 0);

        b.findElement(By.id("continue-shopping")).click();
        b.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        b.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

        b.findElement(By.className("shopping_cart_link")).click();
        b.findElement(By.id("checkout")).click();
        b.findElement(By.id("first-name")).sendKeys("Test");
        b.findElement(By.id("last-name")).sendKeys("User");
        b.findElement(By.id("postal-code")).sendKeys("12345");
        b.findElement(By.id("continue")).click();
        soft.assertEquals(b.findElement(By.className("summary_subtotal_label")).getText(), "Item total: $39.98");

        b.findElement(By.id("finish")).click();
        soft.assertEquals(b.findElement(By.className("complete-header")).getText(), "Thank you for your order!");
        b.findElement(By.id("back-to-products")).click();

        b.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        b.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(500);
        b.findElement(By.id("reset_sidebar_link")).click();
        Thread.sleep(500);
        b.navigate().refresh();
        Thread.sleep(1000);
        soft.assertEquals(b.findElements(By.className("shopping_cart_badge")).size(), 0);
        soft.assertEquals(b.findElements(By.xpath("//button[starts-with(@id,'remove')]")).size(), 0);

        b.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(500);
        b.findElement(By.id("logout_sidebar_link")).click();
        soft.assertTrue(b.findElement(By.id("login-button")).isDisplayed());

        soft.assertAll();
    }

    @Test
    public void testcase2() throws InterruptedException {

        b.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        b.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(500);
        b.findElement(By.id("checkout")).click();
        Thread.sleep(500);

        b.findElement(By.id("continue")).click();
        String error = b.findElement(By.cssSelector("[data-test='error']")).getText();
        Assert.assertEquals(error, "Error: First Name is required");

        b.findElement(By.id("first-name")).sendKeys("Test");
        b.findElement(By.id("last-name")).sendKeys("User");
        b.findElement(By.id("postal-code")).sendKeys("12345");
        b.findElement(By.id("continue")).click();
        Thread.sleep(500);

        b.findElement(By.id("finish")).click();
        Thread.sleep(500);

        String message = b.findElement(By.className("complete-header")).getText();
        Assert.assertEquals(message, "Thank you for your order!");
    }

    @Test
    public void testcase3() throws InterruptedException {

        b.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        b.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();

        Thread.sleep(1000);

        b.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);

        List<WebElement> items = b.findElements(By.className("cart_item"));
        Assert.assertEquals(items.size(), 2);

        b.findElement(By.id("remove-sauce-labs-onesie")).click();
        Thread.sleep(1000);

        items = b.findElements(By.className("cart_item"));
        Assert.assertEquals(items.size(), 1);

        b.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(500);
        b.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(500);

        Assert.assertTrue(b.findElement(By.id("login-button")).isDisplayed());
    }

    @AfterMethod
    public void close() {
        b.quit();
    }
}