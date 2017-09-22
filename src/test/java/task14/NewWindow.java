package task14;

        import org.junit.After;
        import org.junit.Before;
        import org.junit.Test;
        import org.openqa.selenium.*;
        import org.openqa.selenium.chrome.ChromeDriver;
        import org.openqa.selenium.remote.DesiredCapabilities;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import java.util.List;

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
        countriesPage();

       List<WebElement> countriesList = driver.findElements(By.cssSelector("#content tr td:nth-child(5) > a"));
countriesList.get(0).click();

        List<WebElement> externalLinkList = driver.findElements(By.cssSelector("#content tbody i"));
int qtyExternalLinks = externalLinkList.size();

for (int i = 0; i < qtyExternalLinks; i++){
        externalLinkList.get(0).click();
}
 








        for (int i = 1; i <= 3; i++) {
            WebElement firstDuck = driver.findElement(By.cssSelector("#box-most-popular li:first-child"));
            firstDuck.click();

// есть ли на странице Size Select
            List<WebElement> sizeSelect = driver.findElements(By.cssSelector("[name^=options]"));
            if (sizeSelect.size() > 0) {
                sizeSelect.get(0).sendKeys("s");
            }

            WebElement addToCartButton = driver.findElement(By.cssSelector("[name=add_cart_product]"));
            addToCartButton.click();
// подождать, пока счётчик товаров в корзине увеличится на единицу
            wait.until(ExpectedConditions.attributeContains
                    (driver.findElement(By.cssSelector("span.quantity")), "textContent", Integer.toString(i)));
            driver.navigate().back();
        }

// переход в корзину
        WebElement cart = driver.findElement(By.cssSelector("#cart a.link"));
        cart.click();


// строка в таблице как WebElement списка
        List<WebElement> orderTable = driver.findElements(By.cssSelector("#order_confirmation-wrapper tr"));
// количество строчек заказа =  количество строчек в таблице - 5
        int orderQuantityOfRows = orderTable.size() - 5;

        for (int i = 0; i < orderQuantityOfRows; i++) {
            WebElement boxCheckoutCart = driver.findElement(By.cssSelector("#box-checkout-cart"));
            // список из маленьких картинок. Может быть длиной 3, 2 или 0.
            List<WebElement> cheat = boxCheckoutCart.findElements(By.cssSelector(".inact, .act"));
            if (cheat.size() > 0) {
                boxCheckoutCart.findElement(By.cssSelector("li:first-child > a")).click();
            }

            // находим кнопку удаления и кликаем
            WebElement removeButton = driver.findElement(By.cssSelector("[name=remove_cart_item]"));
            removeButton.click();
            // ждём пока малекькая картинка исчезнет, если она есть
            if (cheat.size() > 0) wait.until(ExpectedConditions.stalenessOf(cheat.get(0)));
        }
    }

    public void countriesPage() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        wait.until(titleIs("Countries | My Store"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
