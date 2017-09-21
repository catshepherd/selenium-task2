package task11;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class CustomerRegistration {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/en/create_account");
        wait.until(titleIs("Create Account | My Store"));
    }

    @Test
    public void newCustomerRegistration() {
        // создать нового пользовтеля
        WebElement firstName = driver.findElement(By.cssSelector("[name=firstname]"));
        firstName.sendKeys("Vadym");
        WebElement lastName = driver.findElement(By.cssSelector("[name=lastname]"));
        lastName.sendKeys("Nesushchenko");
        WebElement address1 = driver.findElement(By.cssSelector("[name=address1]"));
        address1.sendKeys("134 Str SE");
        WebElement postCode = driver.findElement(By.cssSelector("[name=postcode]"));
        postCode.sendKeys("98036");
        WebElement city = driver.findElement(By.cssSelector("[name=city]"));
        city.sendKeys("Lynnwood");
        WebElement selectionArrow = driver.findElement(By.cssSelector(".select2-selection__arrow"));
        selectionArrow.click();
        WebElement select2SearchField = driver.findElement(By.cssSelector(".select2-search__field"));
        select2SearchField.sendKeys("United States" + Keys.ENTER);
        WebElement zoneCode = driver.findElement(By.cssSelector("select[name=zone_code]"));
        zoneCode.sendKeys("Washington");
        WebElement email = driver.findElement(By.cssSelector("[name=email]"));
         // генерируем уникальный имейл
        String unicEmail = getEmail();
        email.sendKeys(unicEmail);
        WebElement phone = driver.findElement(By.cssSelector("[name=phone]"));
        phone.sendKeys("+14252660411");
        WebElement password = driver.findElement(By.cssSelector("[name=password]"));
        password.sendKeys("Qwerty!123");
        WebElement confirmedPassword = driver.findElement(By.cssSelector("[name=confirmed_password]"));
        confirmedPassword.sendKeys("Qwerty!123");
        WebElement createAccount = driver.findElement(By.cssSelector("[name=create_account]"));
        createAccount.click();
        // первый выход из аккаунта
        WebElement logOut = driver.findElement(By.cssSelector(logOut()));
        logOut.click();
        // вход в аккаунт
        WebElement email1 = driver.findElement(By.cssSelector("[name=email]"));
        email1.sendKeys(unicEmail);
        WebElement password1 = driver.findElement(By.cssSelector("[name=password]"));
        password1.sendKeys("Qwerty!123");
        WebElement login = driver.findElement(By.cssSelector("[name=login]"));
        login.click();
        // второй выход из аккаунта
        WebElement logOut1 = driver.findElement(By.cssSelector(logOut()));
        logOut1.click();
    }

    private String logOut() {
        return "#box-account [class=list-vertical] li:nth-child(4) a";
    }
    
      // разные имейлы
    private String getEmail() {
        String emailName = "not_sugar";
        String domainName = "@mail.ru";
        String email;

        Date date = new Date();
        long millis = date.getTime();
        email = emailName + millis + domainName;
        return email;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
