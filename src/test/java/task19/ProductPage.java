package task19;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductPage extends Page {
    public static int count = 1;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[name^=options]")
    public List<WebElement> sizeSelectDropDown;

    @FindBy(css = "[name=add_cart_product]")
    public WebElement addToCartButton;

    @FindBy(css = "#cart a.link")
    public WebElement cart;

    @FindBy(css = "span.quantity")
    public WebElement qtyOfGoodsInCart;

    // есть ли на странице Size Select
    public void sizeSelect() {
        if (sizeSelectDropDown.size() > 0) {
            sizeSelectDropDown.get(0).sendKeys("s");
        }
    }

    // добавить в корзину
    public void addToCart() {
        addToCartButton.click();
    }

    // подождать, пока счётчик товаров в корзине увеличится на единицу
    public void waitQuantity() {
        wait.until(ExpectedConditions.attributeContains
                (qtyOfGoodsInCart, "textContent", Integer.toString(count)));
        count++;
        driver.navigate().back();
    }

    // переход в корзину
    public CartPage goToCart() {
        cart.click();
        return new CartPage(driver);
    }
}
