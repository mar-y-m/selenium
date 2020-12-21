package cian;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


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

    public void waitForPageToLoad() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int timeWaitedInMilliseconds = 0;
        int maxWaitTimeInMilliseconds = 2000;

        while (timeWaitedInMilliseconds < maxWaitTimeInMilliseconds) {
            if (js.executeScript("return document.readyState").equals("interactive")) {
                System.out.println("Waited interactive: " + timeWaitedInMilliseconds);
                break;
            }
            waitElementsReload(100);
            timeWaitedInMilliseconds += 100;
        }

        timeWaitedInMilliseconds = 0;
        while (!js.executeScript("return document.readyState").equals("complete")) {
            //System.out.println("waiting !!!!");
            waitElementsReload(500);
            timeWaitedInMilliseconds += 500;
            if (timeWaitedInMilliseconds == 10000) {
                break;
            }
        }
    }


    /**
     * thread sleep
     *
     * @param ms time in milliseconds
     */
    protected void waitElementsReload(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            throw new IllegalArgumentException("error"+ e);
        }
    }


    @After
    public void Stop() {
        driver.quit();
        //WebDriverUtils.killDrivers();
        driver = null;
    }

}
