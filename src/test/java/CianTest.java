import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.By.*;

public class CianTest {

    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start(){
        System.setProperty("webdriver.gecko.driver","C:\\workspace\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }
//    @After
//    public void stop(){
//        driver.quit();
//        driver = null;
//    }

    @Test
    public void test(){
        driver.get("https://voronezh.cian.ru/");
        //1. выбрать правильную линку тип объявления

        setOfferType("Снять");
        System.out.println(driver.findElement(xpath("//ul[@data-name='FiltersTabs']/li[contains(@class,'active')]/a")).getText());

        //2. отметить правильный чекбокс тип жилья
        //3. задать правильное количество комнат

        //4. задать правильный рендж цен
        setPriceRange("f","500000");

        //5. выбрать правильный город
        setCity("Воронеж");

        //6. нажать поиск
        //7. выбрать результаты с первой страницы из дива оффер
        //7,5. сохранить результаты в эксельку

    }

    private void setOfferType(String requiredType) {
        //здесь надо написать как определить что активная линка правильная
        WebElement activeTypeFilterLink = driver.findElement(xpath("//ul[@data-name='FiltersTabs']/li[contains(@class,'active')]/a"));
        String activeLinkName = activeTypeFilterLink.getText();
        if(!activeLinkName.equals(requiredType)){
        WebElement correctType = driver.findElement(By.ByLinkText.linkText(requiredType));
            correctType.click();
        }
    }
    private void setRoomsNumber(){

    }

    private void setPriceRange(String from, String to){
        WebElement priceFilterButton = driver.findElement(xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']/button"));
        priceFilterButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']/div[contains(@class,'dropdown')]")));
        driver.findElement(By.xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']//input[@placeholder='от']")).sendKeys(from);
        driver.findElement(By.xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']//input[@placeholder='до']")).sendKeys(to);
    }

    private void setCity(String city){
        WebElement cityInputField = driver.findElement(By.xpath("//div[@data-name='Filters']//div[@data-mark='FilterGeo']//input"));
        cityInputField.sendKeys(city);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-name='SuggestionPopup']")));
        //driver.findElements(ByLinkText.linkText(city)).get(0).click();
    }

}
