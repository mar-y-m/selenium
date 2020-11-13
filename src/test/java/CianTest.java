import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.By.*;

public class CianTest {

    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start(){
        System.setProperty("webdriver.gecko.driver","C:\\workspace\\geckodriver.exe");
        driver = new FirefoxDriver();
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
        //5. выбрать правильный город
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

}
