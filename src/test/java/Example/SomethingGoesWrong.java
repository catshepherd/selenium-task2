package Example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class SomethingGoesWrong {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.cssSelector("[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=login]")).click();
        wait.until(titleIs("Countries | My Store"));
    }

    @Test
    public void stateAlphabetVerification() {
        // получаем список всех строк стран в виде строк
        List<WebElement> allCountryRows = driver.findElements(By.cssSelector(".row"));
        // проходим циклом, получаем отдельную строку в виде списка ячеек
        for (WebElement joinedCountryColumns : allCountryRows) {
            List<WebElement> dividedCountryColumns = joinedCountryColumns.findElements(By.tagName("td"));
            // сравнить что зона страны != 0
            if (!dividedCountryColumns.get(5).getText().equals("0")) {
                // переходим на страницу страны получаем StaleElementReferenceException
                dividedCountryColumns.get(4).findElement(By.tagName("a")).click();
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
