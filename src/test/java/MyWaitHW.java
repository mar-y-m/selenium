import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyWaitHW {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
    }

    @Test
    public void waitTest(){

        final By panelHeader = new By.ByXPath("//div[@class='panel-heading']");

        driver.get("http://localhost:9003/litecart/admin/");

        login("admin", "admin");

        Assert.assertTrue(isElementPresent(pathToPanel("app")));
        int numberOfAppLinks = getNumberOfElementsFound(pathToPanel("app"));

        for (int ind = 0; ind < numberOfAppLinks; ind++) {
            WebElement currentLink = getElementWithIndex(pathToPanel("app"), ind);
            String currentLinkText = currentLink.getText();
            System.out.println(currentLinkText);
            currentLink.click();

            Assert.assertTrue(isElementPresent(panelHeader));
            WebElement currentHeader = driver.findElement(panelHeader);
            System.out.println(currentHeader.getText());

            int numberOfDocLinks = getNumberOfElementsFound(pathToPanel("doc"));
            System.out.println(numberOfDocLinks);

            if(numberOfDocLinks>0){
                appSubPanels(numberOfDocLinks, panelHeader);
            }
        }
    }

    private void login(String name, String password){
        driver.findElement(By.name("username")).sendKeys(name);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).submit();
    }

    private boolean isElementPresent(By locator){
        try {
            wait.until((WebDriver d) -> d.findElement(locator));
            return true;
        }catch (InvalidSelectorException | TimeoutException ex){
            System.out.println(ex);
            return false;
        }
    }

    private By pathToPanel(String panelType){
        return new By.ByXPath("//ul[@id='box-apps-menu']//li[contains(@class,'"+panelType+"')]/a");
    }

    private int getNumberOfElementsFound(By by) {
        return driver.findElements(by).size();
    }

    private WebElement getElementWithIndex(By by, int indx) {
        return driver.findElements(by).get(indx);
    }

    private void appSubPanels(int numberOfPanels, By panelHeader){
        for (int i = 0; i < numberOfPanels; i++) {
            WebElement currentSubPanel = getElementWithIndex(pathToPanel("doc"), i);
            String currentSubPanelText = currentSubPanel.getText();
            System.out.println(currentSubPanelText);
            currentSubPanel.click();

            Assert.assertTrue(isElementPresent(panelHeader));
            WebElement currentHeader = driver.findElement(panelHeader);
            System.out.println(currentHeader.getText());
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
