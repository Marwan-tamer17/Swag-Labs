package SwagLabs_Automation_Testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.Assert;
import java.time.Duration;

@Listeners(Reports.class)

public class Checkout {

    WebDriver b;
    @BeforeMethod
    public void pre_conditions(){
        System.setProperty("webdriver.chrome.driver", "E:\\Testing\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
        b = new ChromeDriver();
        b.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }


    @Test (priority = 0)
    public void validData(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("standard_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        b.navigate().to("https://www.saucedemo.com/checkout-step-one.html");

//        WebElement btn3 = b.findElement(By.className("shopping_cart_link"));
//        btn3.click();
//
//        WebElement btn2 = b.findElement(By.id("checkout"));
//        btn2.click();

        WebElement firstname = b.findElement(By.id("first-name"));
        firstname.sendKeys("reto");

        WebElement lastname = b.findElement(By.id("last-name"));
        lastname.sendKeys("reto");

        WebElement postalcode = b.findElement(By.id("postal-code"));
        postalcode.sendKeys(("1234"));

        WebElement btn1 = b.findElement(By.id("continue"));
        btn1.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html",
                "Valid data should move the user to the checkout overview page");

        WebElement btn5 = b.findElement(By.id("finish"));
        btn5.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html",
                "Finishing the order should open the order-complete page");
    }

    @Test (priority = 3)
    public void EmptyData(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("standard_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        b.navigate().to("https://www.saucedemo.com/checkout-step-one.html");

//        WebElement btn3 = b.findElement(By.className("shopping_cart_link"));
//        btn3.click();
//
//        WebElement btn2 = b.findElement(By.id("checkout"));
//        btn2.click();

        WebElement firstname = b.findElement(By.id("first-name"));
        firstname.sendKeys("");

        WebElement lastname = b.findElement(By.id("last-name"));
        lastname.sendKeys("");

        WebElement postalcode = b.findElement(By.id("postal-code"));
        postalcode.sendKeys((""));

        WebElement btn1 = b.findElement(By.id("continue"));
        btn1.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html",
                "Empty form must not let the user proceed to the next step");

        WebElement error = b.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(error.getText(), "Error: First Name is required",
                "Wrong or missing validation message for empty form");
    }

    @Test (priority = 1)
    public void validData_problem(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("problem_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        b.navigate().to("https://www.saucedemo.com/checkout-step-one.html");

//        WebElement btn3 = b.findElement(By.className("shopping_cart_link"));
//        btn3.click();
//
//        WebElement btn2 = b.findElement(By.id("checkout"));
//        btn2.click();

        WebElement firstname = b.findElement(By.id("first-name"));
        firstname.sendKeys("reto");

        WebElement lastname = b.findElement(By.id("last-name"));
        lastname.sendKeys("reto");

        WebElement postalcode = b.findElement(By.id("postal-code"));
        postalcode.sendKeys(("1234"));

        WebElement btn1 = b.findElement(By.id("continue"));
        btn1.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html",
                "Valid data should move problem_user to the checkout overview page");

        WebElement btn5 = b.findElement(By.id("finish"));
        btn5.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html",
                "Finishing the order should open the order-complete page");
    }

    @Test (priority = 4)
    public void EmptyData_problem(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("problem_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        b.navigate().to("https://www.saucedemo.com/checkout-step-one.html");

//        WebElement btn3 = b.findElement(By.className("shopping_cart_link"));
//        btn3.click();
//
//        WebElement btn2 = b.findElement(By.id("checkout"));
//        btn2.click();

        WebElement firstname = b.findElement(By.id("first-name"));
        firstname.sendKeys("");

        WebElement lastname = b.findElement(By.id("last-name"));
        lastname.sendKeys("");

        WebElement postalcode = b.findElement(By.id("postal-code"));
        postalcode.sendKeys((""));

        WebElement btn1 = b.findElement(By.id("continue"));
        btn1.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html",
                "Empty form must not let problem_user proceed");

        WebElement error = b.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(error.getText(), "Error: First Name is required",
                "Wrong or missing validation message for empty form");
    }

    @Test (priority = 2)
    public void validData_error(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("error_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        b.navigate().to("https://www.saucedemo.com/checkout-step-one.html");

//        WebElement btn3 = b.findElement(By.className("shopping_cart_link"));
//        btn3.click();
//
//        WebElement btn2 = b.findElement(By.id("checkout"));
//        btn2.click();

        WebElement firstname = b.findElement(By.id("first-name"));
        firstname.sendKeys("reto");

        WebElement lastname = b.findElement(By.id("last-name"));
        lastname.sendKeys("reto");

        WebElement postalcode = b.findElement(By.id("postal-code"));
        postalcode.sendKeys(("1234"));

        WebElement btn1 = b.findElement(By.id("continue"));
        btn1.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html",
                "Valid data should move problem_user to the checkout overview page");

        WebElement btn5 = b.findElement(By.id("finish"));
        btn5.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html",
                "Finishing the order should open the order-complete page");

    }

    @Test (priority = 5)
    public void EmptyData_error(){
        b.navigate().to("https://www.saucedemo.com/");

        WebElement fullname = b.findElement(By.id("user-name"));
        fullname.sendKeys("error_user");

        WebElement pass = b.findElement(By.id("password"));
        pass.sendKeys("secret_sauce");

        WebElement btn = b.findElement(By.id("login-button"));
        btn.click();

        b.navigate().to("https://www.saucedemo.com/checkout-step-one.html");

//        WebElement btn3 = b.findElement(By.className("shopping_cart_link"));
//        btn3.click();
//
//        WebElement btn2 = b.findElement(By.id("checkout"));
//        btn2.click();

        WebElement firstname = b.findElement(By.id("first-name"));
        firstname.sendKeys("");

        WebElement lastname = b.findElement(By.id("last-name"));
        lastname.sendKeys("");

        WebElement postalcode = b.findElement(By.id("postal-code"));
        postalcode.sendKeys((""));

        WebElement btn1 = b.findElement(By.id("continue"));
        btn1.click();

        Assert.assertEquals(b.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html",
                "Empty form must not let error_user proceed");

        WebElement error = b.findElement(By.cssSelector("[data-test='error']"));
        Assert.assertEquals(error.getText(), "Error: First Name is required",
                "Wrong or missing validation message for empty form");
    }

    @AfterMethod
    public void test_again(){
        b.quit();
    }
}
