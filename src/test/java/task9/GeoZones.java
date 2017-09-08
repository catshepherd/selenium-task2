package task9;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class GeoZones {
    private WebDriver driver;
    private WebDriverWait wait;
    // создаём список стран штатов в формате String
    private static List<String> allCountriesString = new ArrayList<>();

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void statesAlphabetVerification() {
        loginPage();
        // получаем к-во стран
        int allCountriesRowsSize = driver.findElements(By.cssSelector(".row td:nth-child(3) a")).size();
        // проходим циклом по странам
        for (int i = 0; i < allCountriesRowsSize; i++) {
            // получаем список всех стран в виде ссылок
            List<WebElement> allCountries = driver.findElements(By.cssSelector(".row td:nth-child(3) a"));
            // получаем отдельную ссылку и кликаем
            allCountries.get(i).click();
            // получаем список штатов
            List<WebElement> allStates = driver.findElements(By.cssSelector("tr :not(.header) td:nth-child(3)"));
            for (WebElement singleState : allStates) {
                allCountriesString.add(singleState.findElement(By.cssSelector("[selected=selected]")).getText());
            }
            // список с названием штатов отправляется в метод для сравнения
            countryComparison(allCountriesString);
            driver.navigate().back();
        }
    }

    private void loginPage() {
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.cssSelector("[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=login]")).click();
        wait.until(titleIs("Geo Zones | My Store"));
    }

    // метод сравнивает лексически соседние строки списка, если первая больше последующей, то выводит сообщение
    private void countryComparison(List<String> countriesString) {
        for (int i = 0; i < countriesString.size() - 2; i++) {
            if (countriesString.get(i).compareTo(countriesString.get(i + 1)) > 0) {
                System.out.println(countriesString.get(i) + " should be swapped with " + countriesString.get(i + 1));
            }
        }
        // очищаем список
        allCountriesString.clear();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}



