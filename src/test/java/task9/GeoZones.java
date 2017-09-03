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

public class GeoZones {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void geoZonesCountry() {
        loginPage();
        List<WebElement> listOfRows = driver.findElements(By.cssSelector(".row"));
        System.out.println(listOfRows.size());
       // System.out.println(listOfRows);
        for (WebElement x: listOfRows){
            System.out.println(x.getText());}
    }

//    private void countryComparison(String secondCountry) {
//        // присваиваем переменной значение, которое меньше названия любой страны
//        String firstCountry = "A";
//        // если порядок стран верный, то переменной присваиваем новое значение для сравнения со следующей страной
//        if (firstCountry.compareTo(secondCountry) < 0) firstCountry = secondCountry;
//        else {
//            System.out.println(firstCountry + " should be swapped with " + secondCountry);
//        }
//        // System.out.println("First country was set " + firstCountry);
//    }

    private void loginPage() {
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.cssSelector("[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=login]")).click();
        wait.until(titleIs("Geo Zones | My Store"));
    }


//    @After
//    public void stop() {
//        driver.quit();
//        driver = null;
//    }
}


