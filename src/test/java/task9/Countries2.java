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

public class Countries2 {
    private static WebDriver driver;
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
    public void countryAlphabetVerification() {
        // список стран в формате String
        List<String> allCountryString = new ArrayList<>();
        // добавляем в список каждую страну в формате String
        allCountryString.add(dividedCountryColumns().get(4).getText());
        // отправляем список в метод для сравнения
        countryComparison(allCountryString);
    }

    @Test
    public void stateAlphabetVerification() {
        // список ссылок стран со штатами в формате WebElement
        List<WebElement> countryWithStates = new ArrayList<>();
        // получаем список всех строк стран в виде строк
        List<WebElement> allCountryRows = driver.findElements(By.cssSelector(".row"));
        // проходим циклом, получаем отдельную строку в виде списка ячеек
        for (WebElement joinedCountryColumns : allCountryRows) {
            List<WebElement> dividedCountryColumns = joinedCountryColumns.findElements(By.tagName("td"));

            // сравнить что зона страны != 0
            if (!dividedCountryColumns.get(5).getText().equals("0")) {
                // добавляем страну в список
                countryWithStates.add(joinedCountryColumns.findElement(By.cssSelector("a")));
                // PS вот так напрямую почему-то выкидывало исключение:  joinedCountryColumns.findElement(By.cssSelector("a")).click();
            }
        }


        //cells.get(4).findElement(By.tagName("a"))

//        for (WebElement singleCountry : countryWithStates) {
//            singleCountry.click();
//            driver.navigate().back();
//        }

      // список штатов в формате String
       List<String> allStateString = new ArrayList<>();
        countryWithStates.get(0).click();
////                // получаем список всех строк штатов
        List<WebElement> allStateRows = driver.findElements(By.cssSelector("#table-zones tr:not(.header)"));
////                // проходим циклом, получаем отдельную строку в виде списка ячеек
        for (WebElement joinedStateColumns : allStateRows) {
            List<WebElement> dividedStateColumns = joinedStateColumns.findElements(By.tagName("td"));
            // ячейка с названием штата отправляется в список
            allStateString.add(dividedStateColumns.get(2).getText());
        }
        countryComparison(allStateString);
    }

        // метод, который возвращает отдельную строку в виде ячеек td
        private List<WebElement> dividedCountryColumns () {
            List<WebElement> dividedCountryColumns = null;
            // получаем список всех строк стран в виде строк
            List<WebElement> allCountryRows = driver.findElements(By.cssSelector(".row"));
            // проходим циклом, получаем отдельную строку в виде списка ячеек td
            for (WebElement joinedCountryColumns : allCountryRows) {
                dividedCountryColumns = joinedCountryColumns.findElements(By.tagName("td"));
            }
            return dividedCountryColumns;
        }

        // метод, который сравнивает, что последующая строка лексически больше, чем первая

    private void countryComparison(List<String> allCountryString) {
        for (int i = 0; i < allCountryString.size() - 1; i++) {
            // если последующая строка лексически больше, чем первая, то выводим текст- предупреждение
            if (allCountryString.get(i).compareTo(allCountryString.get(i + 1)) > 0)
                System.out.println(allCountryString.get(i) + " should be swapped with " + (allCountryString.get(i + 1)));
        }
    }

//    @After
//    public void stop() {
//        driver.quit();
//        driver = null;
//    }
}


