package task19;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class RunTest {
    private WebDriver driver;

    private MyStorePage myStorePage;
    private ProductPage productPage;
    private CartPage cartPage;

    public RunTest() {
        driver = new ChromeDriver();
        myStorePage = new MyStorePage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test
    public void addRemoveGoods() {
        myStorePage.open();
        // добавить 3 уточки в корзину
        for (int i = 1; i <= 3; i++) {
            myStorePage.goToProductPage();
            productPage.sizeSelect();
            productPage.addToCart();
            productPage.waitQuantity();
        }
        // перейти в корзину
        productPage.goToCart();
        // удалить 3 уточки
        int qty = cartPage.orderQuantityOfRows();
        for (int i = 0; i < qty; i++) {
            cartPage.clickSmallPicture();
            cartPage.removeItem();
        }
    }

    @After
    public void quit() {
        driver.quit();
    }
}
