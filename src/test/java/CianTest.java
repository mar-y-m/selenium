import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;

import static org.openqa.selenium.By.*;

public class CianTest {

    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        System.setProperty("webdriver.gecko.driver", "C:\\workspace\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void test() {
        driver.get("https://voronezh.cian.ru/");

        //1. выбрать правильную линку тип объявления
        setFilterTab("Снять");
        System.out.println(driver.findElement(xpath("//ul[@data-name='FiltersTabs']/li[contains(@class,'active')]/a")).getText());

        //2. отметить правильный чекбокс тип жилья


        //3. задать правильное количество комнат
        List<String> currentListOfRoomsCountValues = Arrays.asList("1", "3");
        setRoomsCount(currentListOfRoomsCountValues);

        //4. задать правильный рендж цен
        setPriceRange("f", "500000");

        //5. выбрать правильный город
        setCity("Воронеж");

        //6. нажать поиск
        driver.findElement(ByXPath.xpath("//div[@data-name='FiltersBlock']//a[@data-mark='FiltersSearchButton']")).click();
        //7. выбрать результаты с первой страницы из дива оффер
        //7,5. сохранить результаты в эксельку

    }

    private boolean isElementPresent(By locator) {
        try {
            wait.until((WebDriver d) -> d.findElement(locator));
            return true;
        } catch (InvalidSelectorException | TimeoutException ex) {
            System.out.println(ex);
            return false;
        }
    }

    private void setFilterTab(String requiredType) {
        WebElement activeTypeFilterLink = driver.findElement(xpath("//ul[@data-name='FiltersTabs']/li[contains(@class,'active')]/a"));
        String activeLinkName = activeTypeFilterLink.getText();
        if (!activeLinkName.equals(requiredType)) {
            WebElement correctType = driver.findElement(By.ByLinkText.linkText(requiredType));
            correctType.click();
        }
    }

    private void setOfferType(List<String> requireOfferTypeList){
        WebElement offerTypeFilterButton = driver.findElement(By.xpath("//button[@title='Квартиру в новостройке и вторичке']"));
        offerTypeFilterButton.click();

        By pathToOfferTypeDropdown = By.xpath("//div[@class='_025a50318d--dropdown--3wfz1']");
        Assert.assertTrue(isElementPresent(pathToOfferTypeDropdown));

        int offerTypeOptionsCount = driver.findElements(By.xpath("ul[@class='_025a50318d--list-inner--3S0Cv']/li")).size();
        for (int i = 0; i < offerTypeOptionsCount; i++) {

        }
    }

    private void setRoomsCount(List<String> listOfRoomsCountValues) {
        WebElement roomsCountFilterButton = driver.findElement(By.xpath("//button[@title='1, 2 комн.']"));
        roomsCountFilterButton.click();

        By pathToRoomsCountDropdown = By.xpath("//div[@class='_025a50318d--dropdown--11qNB']");
        Assert.assertTrue(isElementPresent(pathToRoomsCountDropdown));

        int roomsCountButtonsSize = driver.findElements(By.xpath("//ul[@class='_025a50318d--list--3ybsu']/li")).size();
        for (int i = 0; i < roomsCountButtonsSize; i++) {
            WebElement currentButton = driver.findElements(By.xpath("//ul[@class='_025a50318d--list--3ybsu']/li/button")).get(i);

            if (listOfRoomsCountValues.contains(currentButton.getText())) {

                if(!currentButton.getAttribute("class").contains("active")){
                    currentButton.click();
                    Assert.assertTrue(currentButton.getAttribute("class").contains("active"));
                }
                else{
                    System.out.println("Button "+currentButton.getText()+" is already selected");
                }
            }
            else{
                if(currentButton.getAttribute("class").contains("active")){
                    currentButton.click();
                    Assert.assertFalse(currentButton.getAttribute("class").contains("active"));
                }
                else{
                    System.out.println("Button "+currentButton.getText()+" is already not selected");
                }
            }
        }

    }

    private void setPriceRange(String from, String to) {
        driver.findElement(xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']/button")).click();
        Assert.assertTrue(isElementPresent(xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']/div[contains(@class,'dropdown')]")));
        driver.findElement(By.xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']//input[@placeholder='от']")).sendKeys(from);
        driver.findElement(By.xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']//input[@placeholder='до']")).sendKeys(to);
    }

    private void setCity(String city) {
        WebElement cityInputField = driver.findElement(By.xpath("//div[@data-name='Filters']//div[@data-mark='FilterGeo']//input"));
        cityInputField.sendKeys(city);
        Assert.assertTrue(isElementPresent(xpath("//div[@data-name='SuggestionPopup']")));
        driver.findElements(xpath("//div[@data-name='SuggestionPopup']//span[@title='Воронеж']")).get(0).click();
    }

}
