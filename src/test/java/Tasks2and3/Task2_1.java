package Tasks2and3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task2_1 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }
    @Test
    public void myFirstTest(){
        driver.get("https://www.opendrive.com");
        wait.until(titleIs("OpenDrive | Cloud Storage, Notes, and Task Management"));
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}

