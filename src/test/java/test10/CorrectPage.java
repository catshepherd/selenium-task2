package task10;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class CorrectPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
       // driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/en/");
        wait.until(titleIs("Online Store | My Store"));
    }

    @Test
    public void compareVariables() {
        // первый WebElement списка на главной странице
        WebElement firstGoods = driver.findElement(By.cssSelector("#box-campaigns ul li:first-child"));

        // название продукта на главной странице
        WebElement firstGoodsName = firstGoods.findElement(By.cssSelector(".name"));
        String duckText1 = firstGoodsName.getText();

        // обычная цена продукта на главной странице
        WebElement firstGoodsRegularPrice = firstGoods.findElement(By.cssSelector(".regular-price"));
        String regularPriceText1 = getPriceText(firstGoodsRegularPrice);
        String[] regularPriceColor1 = getPriceColor(firstGoodsRegularPrice);
        double regularPriceFontSize1 = getPriceFontSize(firstGoodsRegularPrice);
        String regularPriceLineThrough1 = getLineThrough(firstGoodsRegularPrice);

        // акционная цена продукта на главной странице
        WebElement firstGoodsCampaignPrice = firstGoods.findElement(By.cssSelector(".campaign-price"));
        String campaignPriceText1 = getPriceText(firstGoodsCampaignPrice);
        String[] campaignPriceColor1 = getPriceColor(firstGoodsCampaignPrice);
        double campaignPriceFontSize1 = getPriceFontSize(firstGoodsCampaignPrice);
        boolean campaignPriceFontWeight1 = getFontWeight(firstGoodsCampaignPrice);

        // линк на продукт
        WebElement firstGoodsLink = firstGoods.findElement(By.cssSelector(".link"));
        firstGoodsLink.click();

        // название продукта на странице продукта
        WebElement goodsName = driver.findElement(By.cssSelector("#box-product .title"));
        String duckText2 = goodsName.getText();

        // обычная цена продукта на странице продукта
        WebElement goodsRegularPrice = driver.findElement(By.cssSelector(".regular-price"));
        String regularPriceText2 = getPriceText(goodsRegularPrice);
        String[] regularPriceColor2 = getPriceColor(goodsRegularPrice);
        double regularPriceFontSize2 = getPriceFontSize(goodsRegularPrice);
        String regularPriceLineThrough2 = getLineThrough(goodsRegularPrice);

        // акционная цена продукта на странице продукта
        WebElement goodsCampaignPrice = driver.findElement(By.cssSelector(".campaign-price"));
        String campaignPriceText2 = getPriceText(goodsCampaignPrice);
        String[] campaignPriceColor2 = getPriceColor(goodsCampaignPrice);
        double campaignPriceFontSize2 = getPriceFontSize(goodsCampaignPrice);
        boolean campaignPriceFontWeight2 = getFontWeight(goodsCampaignPrice);

        // сравнение названий продукта на главной странице и на странице продукта
        assertTrue(duckText1.equals(duckText2));
        // сравнение постоянной цены продукта на главной странице и на странице продукта
        assertTrue(regularPriceText1.equals(regularPriceText2));
        // сравнение акционной цены продукта на главной странице и на странице продукта
        assertTrue(campaignPriceText1.equals(campaignPriceText2));
        // сравнение размеров шрифта постоянной цены и акционной на главной странице
        assertTrue(regularPriceFontSize1 < campaignPriceFontSize1);
        // сравнение размеров шрифта постоянной цены и акционной на странице товара
        assertTrue(regularPriceFontSize2 < campaignPriceFontSize2);
        // сравнение цвета шрифта постоянной цены на главной странице
        assertTrue(regularPriceColor1[1].equals(regularPriceColor1[3]) && regularPriceColor1[1].equals(regularPriceColor1[5]));
        // сравнение цвета шрифта акционной цены на главной странице
        assertTrue(campaignPriceColor1[3].equals("0") && campaignPriceColor1[5].equals("0"));
        // сравнение цвета шрифта постоянной цены на странице продукта
        assertTrue(regularPriceColor2[1].equals(regularPriceColor2[3]) && regularPriceColor2[1].equals(regularPriceColor2[5]));
        // сравнение цвета шрифта акционной цены на странице продукта
        assertTrue(campaignPriceColor2[3].equals("0") && campaignPriceColor2[5].equals("0"));
        // проверка, что обычная цена зачеркнутая на главной странице
        assertTrue(regularPriceLineThrough1.equals("line-through"));
        // проверка, что обычная цена зачеркнутая на странице продукта
        assertTrue(regularPriceLineThrough2.equals("line-through"));
        // проверка, что акционная цена жирная на главной странице
        assertTrue(campaignPriceFontWeight1);
        // проверка, что акционная цена жирная на странице продукта
        assertTrue(campaignPriceFontWeight2);
    }

    public String getPriceText(WebElement goodsPrice) {
        return goodsPrice.getText();
    }

    public String[] getPriceColor(WebElement goodsPrice) {
        String priceColor = goodsPrice.getCssValue("color");
        String[] arr = priceColor.split("[(, )]");
        return arr;
    }

    public double getPriceFontSize(WebElement goodsPrice) {
        String fontSizeString = goodsPrice.getCssValue("font-size");
        String[] arr = fontSizeString.split("[a-z]");
        return Double.parseDouble(arr[0]);
    }

    public String getLineThrough(WebElement goodsPrice) {
        return goodsPrice.getCssValue("text-decoration-line");
    }

    public boolean getFontWeight(WebElement goodsPrice) {
        String fontWeight = goodsPrice.getCssValue("font-weight");
        if ((driver.getClass().toString()).contains("Firefox") && fontWeight.equals("900") && driver.getTitle().equals("Online Store | My Store"))
            return true;
        if ((driver.getClass().toString()).contains("Firefox") && fontWeight.equals("700")
                && driver.getTitle().equals("Yellow Duck | Subcategory | Rubber Ducks | My Store"))
            return true;
        else if ((driver.getClass().toString()).contains("Chrome") && fontWeight.equals("bold"))
            return true;
            // тут где-то нужно написать условие для IE
        else return false;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
