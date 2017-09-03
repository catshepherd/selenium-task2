package task8;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class StoreMainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void stickersGoodVerification() {
        driver.get("http://localhost/litecart/en/");
        wait.until(titleIs("Online Store | My Store"));
        // получаем список всех уточек
        List<WebElement> listOfDucks = driver.findElements(By.cssSelector("[class*=hover-light]"));
        // проходим циклом и для каждой уточки проверяем количество стикеров
        for (WebElement duck : listOfDucks) {
            Assert.assertTrue(duck.findElements(By.cssSelector(".sticker")).size() == 1);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}


