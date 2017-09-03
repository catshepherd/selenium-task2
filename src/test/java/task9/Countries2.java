package task9;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Countries2 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void countryAlphabetVerification() {
        loginPage();
        List<WebElement> allCountryRows = driver.findElements(By.cssSelector(".row a"));
        for (WebElement singleCountry : allCountryRows)
            // ячейка с названием страны отправляется в метод для сравнения
            //System.out.println(singleCountry.getText());
            countryComparison(singleCountry.getText());
    }

    @Test
    public void stateAlphabetVerification() {
        //   loginPage();
        // получаем список всех строк стран в виде строк
        List<WebElement> allCountryRows = driver.findElements(By.cssSelector(".row"));
        // проходим циклом, получаем отдельную строку в виде списка ячеек
        for (WebElement joinedCountryColumns : allCountryRows) {
            List<WebElement> dividedCountryColumns = joinedCountryColumns.findElements(By.tagName("td"));
            // сравнить что зона страны != 0
            if (!dividedCountryColumns.get(5).getText().equals("0")) {
                // переходим на страницу страны
                joinedCountryColumns.findElement(By.cssSelector("a")).click();
                // получаем список всех строк штатов
                List<WebElement> allStateRows = driver.findElements(By.cssSelector("#table-zones tr:not(.header)"));
                // проходим циклом, получаем отдельную строку в виде списка ячеек
                for (WebElement joinedStateColumns : allStateRows) {
                    List<WebElement> dividedStateColumns = joinedStateColumns.findElements(By.tagName("td"));
                    // ячейка с названием штата отправляется в метод для сравнения
                    System.out.println(dividedStateColumns.get(2).getText());
                    // countryComparison(dividedStateColumns.get(2).getText());
                }
            }
        }
    }

    private void countryComparison(String secondCountry) {
        // присваиваем переменной значение, которое меньше названия любой страны
        String firstCountry = "A";
        // если порядок стран верный, то переменной присваиваем новое значение для сравнения со следующей страной
        if (firstCountry.compareTo(secondCountry) < 0) firstCountry = secondCountry;
        else {
            System.out.println(firstCountry + " should be swapped with " + secondCountry);
        }
        // System.out.println("First country was set " + firstCountry);
    }

    private void loginPage() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.cssSelector("[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=login]")).click();
        wait.until(titleIs("Countries | My Store"));
    }


//    @After
//    public void stop() {
//        driver.quit();
//        driver = null;
//    }
}


