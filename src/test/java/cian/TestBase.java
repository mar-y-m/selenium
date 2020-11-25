package cian;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;


public class TestBase {

    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        System.setProperty("webdriver.gecko.driver", "C:\\workspace\\geckodriver.exe");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
    }


    //WebElement activeTypeFilterLink = driver.findElement(xpath("//ul[@data-name='FiltersTabs']/li[contains(@class,'active')]/a"));


    boolean areElementsPresent(By locator, int time) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        boolean a = driver.findElements(locator).size() > 0;
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        return a;
    }


    public boolean isElementPresent(By locator) {
        try {
            wait.until((WebDriver d) -> d.findElement(locator));
            return true;
        } catch (InvalidSelectorException | TimeoutException ex) {
            System.out.println(ex);
            return false;
        }
    }

    boolean areElementsPresent(By locator) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        return driver.findElements(locator).size() > 0;
    }

    @After
    public void Stop() {
        driver.quit();
        //WebDriverUtils.killDrivers();
        driver = null;
    }

}
