import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class FirstTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        System.setProperty("webdriver.gecko.driver","C:\\workspace\\geckodriver.exe");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void MyFirstTest(){
        driver.get("http://www.google.com/");
        System.out.println(driver.getTitle());
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
