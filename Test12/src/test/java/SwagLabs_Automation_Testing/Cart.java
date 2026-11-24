package SwagLabs_Automation_Testing;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.util.List;

@Listeners(Reports.class)


public class Cart {
    WebDriver driver;
    @BeforeMethod
    public void pre_conditions() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "E:\\Testing\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = driver.findElement(By.id("user-name"));
        fullname.sendKeys("standard_user");

        WebElement pass = driver.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = driver.findElement(By.id("login-button"));
        btn.click();

        Thread.sleep(2000);
    }

    @Test
    public void open_cart_page() throws InterruptedException {
        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();

        Thread.sleep(2000);

        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("cart.html"));
    }

    @Test
    public void product_details_match_home_page() throws InterruptedException {

        String expectedName = "Sauce Labs Backpack";
        String expectedDesc = "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.";
        String expectedPrice = "$29.99";

        WebElement addBackpack = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addBackpack.click();

        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();


        Thread.sleep(2000);

        WebElement cartNameElement = driver.findElement(By.className("inventory_item_name"));
        String cartName = cartNameElement.getText();

        WebElement cartDescElement = driver.findElement(By.className("inventory_item_desc"));
        String cartDesc = cartDescElement.getText();

        WebElement cartPriceElement = driver.findElement(By.className("inventory_item_price"));
        String cartPrice = cartPriceElement.getText();

        Assert.assertEquals(cartName, expectedName);
        Assert.assertEquals(cartDesc, expectedDesc);
        Assert.assertEquals(cartPrice, expectedPrice);
    }

    // TC3
    @Test
    public void five_products_badge_5() throws InterruptedException {
        WebElement addBackpack = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addBackpack.click();

        WebElement addBikeLight = driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addBikeLight.click();

        WebElement addTShirt = driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"));
        addTShirt.click();

        WebElement addJacket = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));
        addJacket.click();

        WebElement addOnesie = driver.findElement(By.id("add-to-cart-sauce-labs-onesie"));
        addOnesie.click();

        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();

        Thread.sleep(2000);

        List<WebElement> items = driver.findElements(By.className("cart_item"));
        Assert.assertEquals(items.size(), 5);

        WebElement badge = driver.findElement(By.className("shopping_cart_badge"));
        String badgeText = badge.getText();
        Assert.assertEquals(badgeText, "5");
    }

    // TC4
    @Test
    public void remove_one_product_badge_4() throws InterruptedException {
        WebElement addBackpack = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addBackpack.click();

        WebElement addBikeLight = driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addBikeLight.click();

        WebElement addTShirt = driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt"));
        addTShirt.click();

        WebElement addJacket = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));
        addJacket.click();

        WebElement addOnesie = driver.findElement(By.id("add-to-cart-sauce-labs-onesie"));
        addOnesie.click();

        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();

        Thread.sleep(2000);

        WebElement removeBackpack = driver.findElement(By.id("remove-sauce-labs-backpack"));
        removeBackpack.click();

        List<WebElement> items = driver.findElements(By.className("cart_item"));
        Assert.assertEquals(items.size(), 4);

        WebElement badge = driver.findElement(By.className("shopping_cart_badge"));
        String badgeText = badge.getText();
        Assert.assertEquals(badgeText, "4");
    }

    // Integration: Cart & Home page

    // TC5:
    @Test
    public void continue_shopping_keeps_remove_buttons() throws InterruptedException {
        WebElement addBackpack = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addBackpack.click();

        WebElement addBikeLight = driver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addBikeLight.click();

        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();


        Thread.sleep(2000);

        WebElement continueBtn = driver.findElement(By.id("continue-shopping"));
        continueBtn.click();

        WebElement removeBackpack = driver.findElement(By.id("remove-sauce-labs-backpack"));
        String backpackText = removeBackpack.getText();
        Assert.assertEquals(backpackText, "Remove");

        WebElement removeBikeLight = driver.findElement(By.id("remove-sauce-labs-bike-light"));
        String bikeLightText = removeBikeLight.getText();
        Assert.assertEquals(bikeLightText, "Remove");
    }

    //Integration: Cart & Checkout

    // TC6:
    @Test
    public void checkout_opens_information_page() throws InterruptedException {
        WebElement addBackpack = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addBackpack.click();

        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();
        Thread.sleep(2000);

        WebElement checkoutBtn = driver.findElement(By.id("checkout"));
        checkoutBtn.click();

        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("checkout-step-one.html"));
    }


    @AfterMethod
    public void close() {
        driver.quit();
    }
}