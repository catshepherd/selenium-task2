
package task13;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class AddRemoveGoods {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehaviour", "dismiss");
        driver = new ChromeDriver(caps);
        System.out.println(((HasCapabilities) driver).getCapabilities());
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void addOneDuck() {
        mainPage();

        for (int i = 1; i <= 3; i++) {
            WebElement firstDuck = driver.findElement(By.cssSelector("#box-most-popular li:first-child"));
            firstDuck.click();

            // есть ли на странице Size Select
            List<WebElement> sizeSelect = driver.findElements(By.cssSelector("[name^=options]"));
            if (sizeSelect.size() > 0) {
                sizeSelect.get(0).sendKeys("s");
            }

            WebElement addToCartButton = driver.findElement(By.cssSelector("[name=add_cart_product]"));
            addToCartButton.click();
            // подождать, пока счётчик товаров в корзине увеличится на единицу
            wait.until(ExpectedConditions.attributeContains
                    (driver.findElement(By.cssSelector("span.quantity")), "textContent", Integer.toString(i)));
            driver.navigate().back();
        }

        // переход в корзину
        WebElement cart = driver.findElement(By.cssSelector("#cart a.link"));
        cart.click();


         // строка в таблице как WebElement списка
        List<WebElement> orderTable = driver.findElements(By.cssSelector("#order_confirmation-wrapper tr"));
        // количество строчек в таблице
        int totalQuantityOfRows = orderTable.size();
        System.out.println(totalQuantityOfRows);
        // количество строчек заказа =  количество строчек в таблице - 5
        int orderQuantityOfRows = orderTable.size() - 5;

        for (int i = 0; i < orderQuantityOfRows; i++) {
            WebElement removeButton = driver.findElement(By.cssSelector("[name=remove_cart_item]"));
            removeButton.click();
            // подождать пока количество строчек в таблице станет меньше на 1
            // вот здесь часто падает с ошибкой Expected condition failed: waiting for number to be "6". Current number: "0"
            // то есть By.cssSelector("#order_confirmation-wrapper tr") == 0 почему-то
            wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("#order_confirmation-wrapper tr"), totalQuantityOfRows--));
        }
    }

    public void mainPage() {
        driver.get("http://localhost/litecart/en/");
        wait.until(titleIs("Online Store | My Store"));
    }


//    @After
//    public void stop() {
//        driver.quit();
//        driver = null;
//    }
}





        
