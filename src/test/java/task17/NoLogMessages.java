package task17;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class NoLogMessages {

    public static ThreadLocal<EventFiringWebDriver> t1Driver = new ThreadLocal<>();
    public EventFiringWebDriver driver;
    public WebDriverWait wait;

    public static class MyListener extends AbstractWebDriverEventListener {
//        @Override
//        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
//            System.out.println(by);
//        }
//
//        @Override
//        public void afterFindBy(By by, WebElement element, WebDriver driver) {
//            System.out.println(by + " found");
//        }
//
//        @Override
//        public void onException(Throwable throwable, WebDriver driver) {
//            System.out.println(throwable);
//        }

    }

    @Before
    public void start() {
        if (t1Driver.get() != null) {
            driver = t1Driver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        }
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new MyListener());
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void getLogMessages() {
        loginPage();
        String productLocator = "#content tbody td:nth-child(3) [href*=product]";
        // количество продуктов
        int qtyOfRows = driver.findElements(By.cssSelector(productLocator)).size();

        for (int i = 0; i < qtyOfRows; i++) {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div.logotype img"))));
            // список продуктов
            List<WebElement> listOfRows = driver.findElements(By.cssSelector(productLocator));
            listOfRows.get(i).click();

            // System.out.println(driver.manage().logs().getAvailableLogTypes());
            driver.manage().logs().get("browser").forEach(l -> System.out.println(l));

            driver.navigate().back();
        }
    }

    public void loginPage() {
        driver.get(" http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        driver.findElement(By.cssSelector("[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=login]")).click();
        wait.until(titleIs("Catalog | My Store"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
