import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyWaitHW {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void waitTest(){
        driver.get("http://localhost:9003/litecart/admin/");

        System.out.println(driver.getTitle());
        String text = "My Store";
        wait.until(titleIs(text));

        login("admin", "admin");

        String text1 = "Dashboard | My Store";
        wait.until(titleIs(text1));
        System.out.println(driver.getTitle());

        int numberOfAppLinks = getNumberOfElementsFound(By.xpath("//ul[@id='box-apps-menu']//li[contains(@class,'app')]/a"));

        for (int ind = 0; ind < numberOfAppLinks; ind++) {
            WebElement currentLink = getElementWithIndex(By.xpath("//ul[@id='box-apps-menu']//li[contains(@class,'app')]/a"), ind);
            String currentLinkText = currentLink.getText();
            System.out.println(currentLinkText);
            currentLink.click();

            WebElement currentHeader = driver.findElement(By.xpath("//div[@class='panel-heading']"));
            wait.until(ExpectedConditions.visibilityOf(currentHeader));
            System.out.println(currentHeader.getText());

            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

            int numberOfDocLinks = getNumberOfElementsFound(By.xpath("//ul[@id='box-apps-menu']//li[contains(@class,'doc')]/a"));
            System.out.println(numberOfDocLinks);

            if(numberOfDocLinks>0){
                appSubPanels(numberOfDocLinks);
            }
        }
    }

    private void login(String name, String password){
        driver.findElement(By.name("username")).sendKeys(name);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).submit();
    }

    private int getNumberOfElementsFound(By by) {
        return driver.findElements(by).size();
    }

    private WebElement getElementWithIndex(By by, int indx) {
        return driver.findElements(by).get(indx);
    }

    private void appSubPanels(int numberOfPanels){
        for (int i = 0; i < numberOfPanels; i++) {
            WebElement currentSubPanel = getElementWithIndex(By.xpath("//ul[@id='box-apps-menu']//li[contains(@class,'doc')]/a"), i);
            String currentSubPanelText = currentSubPanel.getText();
            System.out.println(currentSubPanelText);
            currentSubPanel.click();

            WebElement currentHeader = driver.findElement(By.xpath("//div[@class='panel-heading']"));
            wait.until(ExpectedConditions.visibilityOf(currentHeader));
            System.out.println(currentHeader.getText());
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
