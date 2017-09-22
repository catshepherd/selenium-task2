package task14;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class NewWindow {
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
    public void verifyNewWindow() {
        loginPage();
        // ID главного окна
        String mainWindow = driver.getWindowHandle();
        // список стран
        List<WebElement> countriesList = driver.findElements(By.cssSelector("#content tr td:nth-child(5) > a"));
        countriesList.get(0).click();
        // список ссылок, которые откорывают новые окна
        List<WebElement> externalLinkList = driver.findElements(By.cssSelector("#content tbody i"));
        // проходим foreach по ссылкам
        for (WebElement singleLink: externalLinkList) {
            singleLink.click();
            // ожидаем, что будет 2 окна
            wait.until(numberOfWindowsToBe(2));
            // сет окон
            Set<String> setWindows = driver.getWindowHandles();
            // находим итератором нужное окно
            for (Iterator<String> it = setWindows.iterator(); it.hasNext(); ) {
                String newWindow = it.next();
                if (!newWindow.equals(mainWindow)) driver.switchTo().window(newWindow);
            }
            // закрыть текущее окно
            driver.close();
            // переход на главное окно
            driver.switchTo().window(mainWindow);
        }
    }
    
    public void loginPage() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.cssSelector("[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=login]")).click();
        wait.until(titleIs("Countries | My Store"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
