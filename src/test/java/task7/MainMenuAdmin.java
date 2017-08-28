package task7;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MainMenuAdmin {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehaviour", "dismiss");
        driver = new ChromeDriver(caps);
        System.out.println(((HasCapabilities) driver).getCapabilities());
        //  driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void adminPage() {
        // вводим логин-пароль для админ.страницы
        loginPage();
        // получаем длину List<WebElement> box-apps-menu
        int size = driver.findElements(By.cssSelector("#box-apps-menu li#app-")).size();
        // проходимся по главному меню циклом
        for (int i = 0; i < size; i++) {
            // кликаем по элементу главного меню
            driver.findElements(By.cssSelector("#box-apps-menu li#app-")).get(i).click();
            // получаем длину списка подменю
            int subSize = driver.findElements(By.cssSelector(".docs li")).size();
            // если длина равна нулю, продолжаем внешний цикл
            if (subSize == 0) {
                // проверка наличия заголовка с тегом h1
                verificationH1Enable();
            }
            // проходимся по подменю циклом
            for (int j = 1; j < subSize; j++) {
                // кликаем по элементу подменю циклом
                driver.findElements(By.cssSelector(".docs li")).get(j).click();
                // проверка наличия заголовка с тегом h1
                verificationH1Enable();
            }
            // возвращаемся "домой"
            driver.findElement(By.cssSelector("[class*=fa-home]")).click();
        }
    }

    public void loginPage() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=login]")).click();
        wait.until(titleIs("My Store"));
    }

    public void verificationH1Enable() {
        try {
            driver.findElement(By.cssSelector("h1"));
        } catch (NoSuchElementException e) {
            System.out.println(driver.getTitle() + " page doesn't contain tag h1");
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}


