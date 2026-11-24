package SwagLabs_Automation_Testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.Assert;
import java.time.Duration;

@Listeners(Reports.class)

public class Integration {

    WebDriver b;
    @BeforeMethod
    public void pre_conditions(){
        System.setProperty("webdriver.chrome.driver", "E:\\Testing\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
        b = new ChromeDriver();
        b.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    // Stories integrated: Login + Home Page + Cart
    @Test (priority = 0)
    public void login_HomePage_Cart(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("standard_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/inventory.html",
                "Valid login should open the Home Page");

        WebElement pageTitle = b.findElement(By.className("title"));
        Assert.assertEquals(pageTitle.getText(), "Products",
                "Home Page should display the Products title");

        WebElement addBackpack = b.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addBackpack.click();

        WebElement removeBackpack = b.findElement(By.id("remove-sauce-labs-backpack"));
        Assert.assertEquals(removeBackpack.getText(), "Remove",
                "Add to cart button should change to Remove on the Home Page");

        WebElement cartBadge = b.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), "1",
                "Cart badge should update to 1 after adding an item from Home Page");

        WebElement cartLink = b.findElement(By.className("shopping_cart_link"));
        cartLink.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/cart.html",
                "Clicking the cart icon should open the Cart page");

        WebElement cartTitle = b.findElement(By.className("title"));
        Assert.assertEquals(cartTitle.getText(), "Your Cart",
                "Cart page should display the Your Cart title");

        WebElement cartItemName = b.findElement(By.className("inventory_item_name"));
        Assert.assertEquals(cartItemName.getText(), "Sauce Labs Backpack",
                "Cart should display the product added from the Home Page");

        WebElement cartItemPrice = b.findElement(By.className("inventory_item_price"));
        Assert.assertEquals(cartItemPrice.getText(), "$29.99",
                "Cart should display the matching price for the added product");
    }

    // Stories integrated: Login + Dynamic Catalogue + Cart
    @Test (priority = 1)
    public void dynamicCatalogue_Cart(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("standard_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        WebElement firstBeforeSort = b.findElement(By.className("inventory_item_name"));
        Assert.assertEquals(firstBeforeSort.getText(), "Sauce Labs Backpack",
                "Default catalogue order should show Sauce Labs Backpack first");

        WebElement lowToHighOption = b.findElement(By.cssSelector("select.product_sort_container option[value='lohi']"));
        lowToHighOption.click();

        WebElement firstAfterSort = b.findElement(By.className("inventory_item_name"));
        Assert.assertEquals(firstAfterSort.getText(), "Sauce Labs Onesie",
                "Sorting by price low to high should move Sauce Labs Onesie to first position");

        WebElement firstPriceAfterSort = b.findElement(By.className("inventory_item_price"));
        Assert.assertEquals(firstPriceAfterSort.getText(), "$7.99",
                "First product price after sorting low to high should be $7.99");

        WebElement addOnesie = b.findElement(By.id("add-to-cart-sauce-labs-onesie"));
        addOnesie.click();

        WebElement cartLink = b.findElement(By.className("shopping_cart_link"));
        cartLink.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/cart.html",
                "Cart icon should navigate to the Cart page");

        WebElement cartItemName = b.findElement(By.className("inventory_item_name"));
        Assert.assertEquals(cartItemName.getText(), "Sauce Labs Onesie",
                "Cart should contain the product selected after sorting the Dynamic Catalogue");

        WebElement cartItemPrice = b.findElement(By.className("inventory_item_price"));
        Assert.assertEquals(cartItemPrice.getText(), "$7.99",
                "Cart price should match the sorted product price");
    }

    // Stories integrated: Dynamic Catalogue + Navigation + Home Page + Cart
    @Test (priority = 2)
    public void dynamicCatalogue_Navigation_Cart(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("standard_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        WebElement backpackLink = b.findElement(By.id("item_4_title_link"));
        backpackLink.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/inventory-item.html?id=4",
                "Clicking a product name should navigate to its Dynamic Catalogue details page");

        WebElement detailName = b.findElement(By.className("inventory_details_name"));
        Assert.assertEquals(detailName.getText(), "Sauce Labs Backpack",
                "Product details page should display the selected product name");

        WebElement detailPrice = b.findElement(By.className("inventory_details_price"));
        Assert.assertEquals(detailPrice.getText(), "$29.99",
                "Product details page should display the selected product price");

        WebElement addDetailBtn = b.findElement(By.id("add-to-cart"));
        addDetailBtn.click();

        WebElement cartBadge = b.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), "1",
                "Adding from product details page should update the Cart badge");

        WebElement backBtn = b.findElement(By.id("back-to-products"));
        backBtn.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/inventory.html",
                "Back to products button should navigate back to the Home Page");

        WebElement removeOnHome = b.findElement(By.id("remove-sauce-labs-backpack"));
        Assert.assertEquals(removeOnHome.getText(), "Remove",
                "Home Page should reflect that the item was added from the details page");

        WebElement cartLink = b.findElement(By.className("shopping_cart_link"));
        cartLink.click();

        WebElement removeInCart = b.findElement(By.id("remove-sauce-labs-backpack"));
        removeInCart.click();

        WebElement continueShopping = b.findElement(By.id("continue-shopping"));
        continueShopping.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/inventory.html",
                "Continue Shopping should navigate from Cart back to the Home Page");

        WebElement addOnHomeAgain = b.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        Assert.assertEquals(addOnHomeAgain.getText(), "Add to cart",
                "Removing the item in Cart should reset its button to Add to cart on the Home Page");
    }

    // Stories integrated: Home Page + Cart + Checkout (+ Navigation)
    @Test (priority = 3)
    public void homePage_Cart_Checkout(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("standard_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        WebElement addBackpack = b.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addBackpack.click();

        WebElement addBikeLight = b.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addBikeLight.click();

        WebElement cartBadge = b.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), "2",
                "Cart badge should show 2 items added from Home Page");

        WebElement cartLink = b.findElement(By.className("shopping_cart_link"));
        cartLink.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/cart.html",
                "Cart icon should open the Cart page");

        WebElement checkoutBtn = b.findElement(By.id("checkout"));
        checkoutBtn.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html",
                "Checkout button should navigate from Cart to Checkout Step One");

        WebElement firstname = b.findElement(By.id("first-name"));
        firstname.sendKeys("reto");

        WebElement lastname = b.findElement(By.id("last-name"));
        lastname.sendKeys("reto");

        WebElement postalcode = b.findElement(By.id("postal-code"));
        postalcode.sendKeys("1234");

        WebElement continueBtn = b.findElement(By.id("continue"));
        continueBtn.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html",
                "Valid data should move the user to the checkout overview page");

        WebElement subtotal = b.findElement(By.className("summary_subtotal_label"));
        Assert.assertEquals(subtotal.getText(), "Item total: $39.98",
                "Checkout overview subtotal should match the sum of Cart items");

        WebElement total = b.findElement(By.className("summary_total_label"));
        Assert.assertEquals(total.getText(), "Total: $43.18",
                "Checkout overview total should include tax on top of item subtotal");

        WebElement finishBtn = b.findElement(By.id("finish"));
        finishBtn.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html",
                "Finishing the order should open the order-complete page");

        WebElement completeHeader = b.findElement(By.cssSelector("[data-test='complete-header']"));
        Assert.assertEquals(completeHeader.getText(), "Thank you for your order!",
                "Order complete page should display confirmation message");

        WebElement backHomeBtn = b.findElement(By.id("back-to-products"));
        backHomeBtn.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/inventory.html",
                "Back Home button should return to the Home Page");

        WebElement addBackpackAgain = b.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        Assert.assertEquals(addBackpackAgain.getText(), "Add to cart",
                "Completing checkout should clear the cart and reset Home Page buttons");
    }

    // Stories integrated: Cart + Navigation + Checkout (+ Home Page)
    @Test (priority = 4)
    public void cart_Navigation_Checkout(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("standard_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        WebElement addBackpack = b.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addBackpack.click();

        WebElement cartLink = b.findElement(By.className("shopping_cart_link"));
        cartLink.click();

        WebElement checkoutBtn = b.findElement(By.id("checkout"));
        checkoutBtn.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html",
                "Checkout button in Cart should open Checkout Step One");

        WebElement cancelBtn = b.findElement(By.id("cancel"));
        cancelBtn.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/cart.html",
                "Cancel button on Checkout Step One should navigate back to Cart");

        WebElement cartItemName = b.findElement(By.className("inventory_item_name"));
        Assert.assertEquals(cartItemName.getText(), "Sauce Labs Backpack",
                "Canceling checkout should preserve the item inside the Cart");

        WebElement cartBadge = b.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), "1",
                "Cart badge should still show 1 after canceling checkout");

        WebElement continueShopping = b.findElement(By.id("continue-shopping"));
        continueShopping.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/inventory.html",
                "Continue Shopping should navigate from Cart back to Home Page");

        WebElement removeOnHome = b.findElement(By.id("remove-sauce-labs-backpack"));
        Assert.assertEquals(removeOnHome.getText(), "Remove",
                "Home Page should keep the item added state after navigating back from Cart");
    }

    // Stories integrated: Login + Cart + Navigation + Logout
    @Test (priority = 5)
    public void login_Cart_Navigation_Logout(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("standard_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        WebElement addBackpack = b.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addBackpack.click();

        WebElement menuBtn = b.findElement(By.id("react-burger-menu-btn"));
        menuBtn.click();

        WebElement logoutLink = b.findElement(By.id("logout_sidebar_link"));
        logoutLink.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/",
                "Logout from the navigation menu should return to the Login page");

        WebElement loginLogo = b.findElement(By.className("login_logo"));
        Assert.assertEquals(loginLogo.getText(), "Swag Labs",
                "Login page logo should be displayed after logout");

        b.navigate().to("https://www.saucedemo.com/inventory.html");

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/",
                "Logged-out user should not be able to access Home Page directly");

        WebElement error = b.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(error.getText(), "Epic sadface: You can only access '/inventory.html' when you are logged in.",
                "Accessing Home Page after logout should show login required error");

        WebElement fullnameAgain = b.findElement(By.id("user-name"));
        fullnameAgain.sendKeys("standard_user");

        WebElement passAgain = b.findElement(By.id("password"));
        passAgain.sendKeys("secret_sauce");

        WebElement btnAgain = b.findElement(By.id("login-button"));
        btnAgain.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/inventory.html",
                "User should be able to log back in after logging out");

        WebElement cartBadgeAfterLogin = b.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadgeAfterLogin.getText(), "1",
                "Cart item added before logout should still be preserved after logging back in");
    }

    @AfterMethod
    public void test_again(){
        b.quit();
    }
}
