

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

public class CountriesAndStates {
    private WebDriver driver;
    private WebDriverWait wait;
    // создаём список стран или штатов в формате String
    private static List<String> allCountriesString = new ArrayList<>();

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
    public void countryAlphabetVerification() {
        // получаем к-во строк стран
        int allCountryRowsSize = driver.findElements(By.cssSelector(".row")).size();
// проходим циклом, получаем отдельную строку в виде списка ячеек
        for (int i = 0; i < allCountryRowsSize; i++) {
// получаем список всех стран в виде строк
            List<WebElement> allCountryRows = driver.findElements(By.cssSelector(".row"));
// получаем отдельную строку в виде списка ячеек
            List<WebElement> dividedCountryColumns = allCountryRows.get(i).findElements(By.tagName("td"));
// имя страны добавляем в список
            allCountriesString.add(dividedCountryColumns.get(4).getText());
        }
    }

    @Test
    public void stateAlphabetVerification() {
// получаем к-во строк стран
        int allCountryRowsSize = driver.findElements(By.cssSelector(".row")).size();
// проходим циклом, получаем отдельную строку в виде списка ячеек
        for (int i = 0; i < allCountryRowsSize; i++) {
// получаем список всех стран в виде строк
            List<WebElement> allCountryRows = driver.findElements(By.cssSelector(".row"));
// получаем отдельную строку в виде списка ячеек
            List<WebElement> dividedCountryColumns = allCountryRows.get(i).findElements(By.tagName("td"));
// сравнить что зона страны != 0
            if (!dividedCountryColumns.get(5).getText().equals("0")) {
// переходим на страницу страны
                dividedCountryColumns.get(4).findElement(By.tagName("a")).click();
// создаём список стран в формате String
                //List<String> allCountriesString = new ArrayList<>();
// получаем список всех строк штатов
                List<WebElement> allStateRows = driver.findElements(By.cssSelector("#table-zones tr:not(.header)"));
// проходим циклом, получаем отдельную строку в виде списка ячеек
                for (WebElement joinedStateColumns : allStateRows) {
                    List<WebElement> dividedStateColumns = joinedStateColumns.findElements(By.tagName("td"));
                    // добавляем штат в список
                    allCountriesString.add(dividedStateColumns.get(2).getText());
                }
                // ячейка с названием штата отправляется в метод для сравнения
                countryComparison(allCountriesString);
                driver.navigate().back();
            }
        }
    }

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

