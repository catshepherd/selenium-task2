package task12;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class AddGoods {
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
    public void addOneDuck() {
        loginPage();

        WebElement catalog = driver.findElement(By.cssSelector("#box-apps-menu li:nth-child(2)"));
        catalog.click();
        WebElement addNewProduct = driver.findElement(By.cssSelector("#content > div a:nth-child(2)"));
        addNewProduct.click();

        // Заполнение значениями General таб
        WebElement statusEnabledRadio = driver.findElement(By.cssSelector("label:nth-child(3) > input[type=radio]"));
        statusEnabledRadio.click();

        WebElement name = driver.findElement(By.cssSelector("#tab-general tr:nth-child(2) [type=text]"));
        name.sendKeys("Steel Duck");

        WebElement code = driver.findElement(By.cssSelector("#tab-general tr:nth-child(3) [type=text]"));
        code.sendKeys("duck04");

        WebElement categoriesFirstCheckbox = driver.findElement(By.cssSelector("[data-name=Root]"));
        categoriesFirstCheckbox.click();

        WebElement categoriesThirdCheckbox = driver.findElement(By.cssSelector("[data-name=Subcategory]"));
        categoriesThirdCheckbox.click();

        // отметить все чекбоксы Product Groups
        List<WebElement> productGroupsList = driver.findElements(By.cssSelector("[name^=product_groups]"));
        for (WebElement singleProductGroup : productGroupsList) {
            singleProductGroup.click();
        }

        WebElement quantity = driver.findElement(By.cssSelector("[name=quantity]"));
        quantity.clear();
        quantity.sendKeys("123");

        WebElement newImagesButton = driver.findElement(By.cssSelector("[type=file]"));
        newImagesButton.sendKeys(new File("Steel_Duck.jpg").getAbsolutePath());

        WebElement dateValidFrom = driver.findElement(By.cssSelector("[name=date_valid_from]"));
        dateValidFrom.sendKeys("01102017");
        WebElement dateValidTo = driver.findElement(By.cssSelector("[name=date_valid_to]"));
        dateValidTo.sendKeys("01112017");

        // переход на Information таб
        WebElement informationTab = driver.findElement(By.cssSelector("#content li:nth-child(2) > a"));
        informationTab.click();

        WebElement manufacturer = driver.findElement(By.cssSelector("[name=manufacturer_id]"));
        // не уверен, что ожидание работает. Без него код не падал ни разу.
        wait.until(ExpectedConditions.visibilityOf(manufacturer));
        manufacturer.sendKeys("a");

        WebElement keywords = driver.findElement(By.cssSelector("[name=keywords]"));
        keywords.sendKeys("duck, steel, handmade");

        WebElement shortDescription = driver.findElement(By.cssSelector("[name^=short_description]"));
        setClipboard("Federal’s Premium Black Cloud Steel (shown here) line added then new 3- and 3 1/2-inch.");
        shortDescription.sendKeys(Keys.CONTROL, "v");

        WebElement description = driver.findElement(By.cssSelector(".trumbowyg-editor"));
        setClipboard("Federal’s Premium Black Cloud Steel (shown here) line added then new 3- and 3 1/2-inch, " +
                "12-gauge loads utilizing #3 shot. At 1,500 and 1450-fps, they get the job done on the toughest of fowl. " +
                "The 3 1/2-inch shells are loaded with #2, BB and BBB, with 4.84 drams equivalent of powder. " +
                "The 3-inch shells come in #2, 3, BB and BBB with 4.23 drams equivalent.");
        description.sendKeys(Keys.CONTROL, "v");

        WebElement headTitle = driver.findElement(By.cssSelector("[name^=head_title]"));
        headTitle.sendKeys("Any head title");

        WebElement metaDescription = driver.findElement(By.cssSelector("[name^=meta_description]"));
        setClipboard("Federal partnered with Patternmaster Chokes for better patterns with their Black Cloud line, " +
                "and feel their project is successful.");
        metaDescription.sendKeys(Keys.CONTROL, "v");

        // переход на Prices таб
        WebElement prices = driver.findElement(By.cssSelector("#content li:nth-child(4) > a"));
        prices.click();

        WebElement purchasePrice = driver.findElement(By.cssSelector("[name=purchase_price]"));
        wait.until(ExpectedConditions.visibilityOf(purchasePrice));
        purchasePrice.clear();
        purchasePrice.sendKeys("1.22");

        WebElement purchasePriceCurrency = driver.findElement(By.cssSelector("[name=purchase_price_currency_code]"));
        purchasePriceCurrency.sendKeys("u");

        WebElement priceUSD = driver.findElement(By.cssSelector("#tab-prices tr:nth-child(2) input[type=text]"));
        priceUSD.sendKeys("1.99");

        WebElement priceEURO = driver.findElement(By.cssSelector("#tab-prices tr:nth-child(3) input[type=text]"));
        priceEURO.sendKeys("1.76");

        WebElement saveButton = driver.findElement(By.cssSelector("[name=save]"));
        saveButton.click();
    }

    public void loginPage() {
        driver.get("http://localhost/litecart/admin");
        driver.findElement(By.cssSelector("[name=username]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=password]")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name=login]")).click();
        wait.until(titleIs("My Store"));
    }

    // This method writes a string to the system clipboard. Otherwise it returns null.
    public static void setClipboard(String str) {
        StringSelection ss = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}


