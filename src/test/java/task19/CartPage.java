package task19;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends Page {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public CartPage open() {
        driver.get("http://localhost/litecart/en/checkout");
        return this;
    }

    // строка в таблице как WebElement списка
    @FindBy(css = "#order_confirmation-wrapper tr")
    private List<WebElement> orderTable;

    // список из маленьких картинок. Может быть длиной 3, 2 или 0.
    @FindBy(css = "#box-checkout-cart .inact, .act")
    private List<WebElement> smallPicturesList;

    // ссылка на картинку-чит
    @FindBy(css = "#box-checkout-cart li:first-child > a")
    private WebElement smallPicture;

    @FindBy(css = "[name=remove_cart_item]")
    private WebElement removeButton;

    // количество строчек заказа
    public int orderQuantityOfRows() {
        return orderTable.size() - 5;
    }

    // кликаем на чит-картинке, если она есть
    public void clickSmallPicture() {
        if (smallPicturesList.size() > 0) {
            smallPicture.click();
        }
    }
