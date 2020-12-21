package hw;

import cian.TestBase;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MyInvisibilityHW  extends TestBase {

    @Test
    public void test(){
        driver.get("http://localhost:9003/litecart/admin");

        driver.findElement(By.name("username")).sendKeys("asd");
        driver.findElement(By.name("password")).sendKeys("asd");
        driver.findElement(By.name("login")).submit();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("notices")));
        System.out.println("Notices are visible");
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='alert alert-danger']")).isDisplayed());
        System.out.println("Element is displayed");

    }
}
