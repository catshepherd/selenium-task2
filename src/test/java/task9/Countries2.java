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
    public void countryAlphabetVerification() {
        List<String> countriesString = new ArrayList<>();
        List<WebElement> allCountryRows = driver.findElements(By.cssSelector(".row a"));
        for (WebElement singleCountry : allCountryRows)
            // ячейка с названием страны отправляется в метод для сравнения
            //  System.out.println(singleCountry.getText());
            countriesString.add(singleCountry.getText());
        System.out.println(countriesString);
      //  countryComparison(countriesString);
    }

//    @Test
//    public void stateAlphabetVerification() {
//        //   loginPage();
//        // получаем список всех строк стран в виде строк
//        List<WebElement> allCountryRows = driver.findElements(By.cssSelector(".row"));
//        // проходим циклом, получаем отдельную строку в виде списка ячеек
//        for (WebElement joinedCountryColumns : allCountryRows) {
//            List<WebElement> dividedCountryColumns = joinedCountryColumns.findElements(By.tagName("td"));
//            // сравнить что зона страны != 0
//            if (!dividedCountryColumns.get(5).getText().equals("0")) {
//                // переходим на страницу страны
//                joinedCountryColumns.findElement(By.cssSelector("a")).click();
//                // получаем список всех строк штатов
//                List<WebElement> allStateRows = driver.findElements(By.cssSelector("#table-zones tr:not(.header)"));
//                // проходим циклом, получаем отдельную строку в виде списка ячеек
//                for (WebElement joinedStateColumns : allStateRows) {
//                    List<WebElement> dividedStateColumns = joinedStateColumns.findElements(By.tagName("td"));
//                    // ячейка с названием штата отправляется в метод для сравнения
//                    System.out.println(dividedStateColumns.get(2).getText());
//                    // countryComparison(dividedStateColumns.get(2).getText());
//                }
//            }
//        }
//    }

    private void countryComparison(List<String> countriesString) {
        for (int i = 0; i < countriesString.size() - 1; i++) {
            int x = countriesString.get(i).compareTo(countriesString.get(i + 1));
               // System.out.println(countriesString.get(i) + " should be swapped with " + countriesString.get(i +
            System.out.println(x);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}


