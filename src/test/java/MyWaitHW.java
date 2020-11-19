import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        loginAsAdmin();

        Assert.assertTrue(isElementPresent(pathToPanel(Dictionary.PANEL)));
        int numberOfAppLinks = getNumberOfElementsFound(pathToPanel(Dictionary.PANEL));
        System.out.println(numberOfAppLinks);

        for (int ind = 0; ind < numberOfAppLinks; ind++) {
            WebElement currentLink = getElementWithIndex(pathToPanel(Dictionary.PANEL), ind);
            String currentPanelLinkText = currentLink.getText();
            System.out.println("Current panel is "+ currentPanelLinkText);
            currentLink.click();

            Assert.assertTrue(isElementPresent(panelHeader));
            String currentHeaderName = driver.findElement(panelHeader).getText();

            System.out.println("Current header is " + currentHeaderName);

            int numberOfDocLinks = getNumberOfElementsFound(pathToPanel(Dictionary.SUBPANEL));
            System.out.println(numberOfDocLinks);

            //проверка, что, если хэдер не совпадает с текстом ссылки, он совпадает с текстом первой сабпанели
            if(!currentPanelLinkText.equals(currentHeaderName)){
                Assert.assertTrue(numberOfDocLinks>0);
                String firstSubpanelLinkText = getElementWithIndex(pathToPanel(Dictionary.SUBPANEL), 0).getText();
                System.out.println("First subpanel link text is "+firstSubpanelLinkText);
                Assert.assertEquals(firstSubpanelLinkText, currentHeaderName);
                System.out.println("Current Header is correct");
            }
            else{
                System.out.println("Current Header is correct");}

            if(numberOfDocLinks>0){
                appSubPanels(numberOfDocLinks, panelHeader);
            }
        }
    }

    private void loginAsAdmin(){
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).submit();
    }

    private boolean isElementPresent(By locator){
        try {
            wait.until(d -> d.findElement(locator));
            return true;
        }catch (InvalidSelectorException | TimeoutException ex){
            System.out.println(ex);
            return false;
        }
    }

    private By pathToPanel(Dictionary panelType){
        return new By.ByXPath("//ul[@id='box-apps-menu']//li[contains(@class,'"+panelType.getType()+"')]/a");
    }

    private int getNumberOfElementsFound(By by) {
        return driver.findElements(by).size();
    }

    private WebElement getElementWithIndex(By by, int indx) {
        return driver.findElements(by).get(indx);
    }

    private void appSubPanels(int numberOfPanels, By panelHeader){
        for (int i = 0; i < numberOfPanels; i++) {
            WebElement currentSubPanel = getElementWithIndex(pathToPanel(Dictionary.SUBPANEL), i);
            String currentSubPanelText = currentSubPanel.getText();
            System.out.println("Current subpanel is "+currentSubPanelText);
            currentSubPanel.click();

            Assert.assertTrue(isElementPresent(panelHeader));
            String currentHeaderName = driver.findElement(panelHeader).getText();
            System.out.println("Current header is " + currentHeaderName);

            if(!currentSubPanelText.equals(currentHeaderName)){

            //проверка, что у сеттингов своя атмосфера
            if(currentHeaderName.equals("Settings")){
                List<WebElement> settingsSubPanels = driver.findElements(By.xpath("//li[@data-code='settings']//li[contains(@class,'doc')]/a"));
                List<String> subpanelNames = settingsSubPanels.stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList());
                Assert.assertTrue(subpanelNames.stream().anyMatch(s -> s.equals(currentSubPanelText)));
                System.out.println("Current Header is correct");
            }

            //проверка, что у одной единственной панели скан файлз дополнительный текст в хэдере
            if(currentSubPanelText.equals("Scan Files")){
                Assert.assertEquals("Scan Files For Translations", currentHeaderName);
                System.out.println("Current Header is correct");
            }
            }
            else{
                Assert.assertEquals(currentSubPanelText,currentHeaderName);
                System.out.println("Current Header is correct");
            }
        }
    }

    public enum Dictionary{
        PANEL("app"),
        SUBPANEL("doc");

        private final String type;

        Dictionary(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static Dictionary fromString(String s){
            return Arrays.stream(Dictionary.values())
                    .filter(e -> e.getType().equals(s))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown dictionary value "+ s));
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
