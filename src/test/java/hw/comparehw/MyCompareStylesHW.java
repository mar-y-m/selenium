package hw.comparehw;

import cian.TestBase;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

public class MyCompareStylesHW extends TestBase {

    @Test
    public void test() {
        driver.get("http://localhost:9003/litecart/");

        final By mainPageProductTitleLocator = By.xpath("//section[@id='box-campaign-products']//h4");
        final By mainPageProductRegularPriceLocator = By.xpath("//section[@id='box-campaign-products']//*[@class='regular-price']");
        final By mainPageProductCampaignPriceLocator = By.xpath("//section[@id='box-campaign-products']//*[@class='campaign-price']");
        final By productLink = By.xpath("//section[@id='box-campaign-products']//img");
        final By productPageTitleLocator = By.xpath("//article[@id='box-product']//h1");
        final By productPageRegularPriceLocator = By.xpath("//div[@class='buy_now']//*[@class='regular-price']");
        final By productPageCampaignPriceLocator = By.xpath("//div[@class='buy_now']//*[@class='campaign-price']");

        Assert.assertTrue(isElementPresent(mainPageProductTitleLocator));
        DuckObject mainPageDuck = getDuckInfo(mainPageProductTitleLocator, mainPageProductRegularPriceLocator, mainPageProductCampaignPriceLocator);
        driver.findElement(productLink).click();

        Assert.assertTrue(isElementPresent(productPageTitleLocator));
        DuckObject productPageDuck = getDuckInfo(productPageTitleLocator, productPageRegularPriceLocator, productPageCampaignPriceLocator);

        System.out.println("Проверка элементов на главной странице и странице товара: ");
        compareDucks(mainPageDuck, productPageDuck);

        System.out.println("Проверка цвета и стиля текста:");
        System.out.println("Цвет и стиль на главной странице:");
        compareTextColor(mainPageDuck);
        compareTextSize(mainPageDuck);
        System.out.println("-> Проверка, что старый текст зачеркнут: "+mainPageDuck.isOldPriceTextLineThrough());
        System.out.println("-> Проверка, что новый текст жирный: "+mainPageDuck.isNewPriceTextBold());
        System.out.println("Цвет и стиль на странице товара:");
        compareTextColor(productPageDuck);
        compareTextSize(productPageDuck);
        System.out.println("-> Проверка, что старый текст зачеркнут: "+productPageDuck.isOldPriceTextLineThrough());
        System.out.println("-> Проверка, что новый текст жирный: "+productPageDuck.isNewPriceTextBold());
    }

    private DuckObject getDuckInfo(By titleLocator, By oldPriceLocator, By newPriceLocator) {
        String mainPageTitle = driver.findElement(titleLocator).getText();
        String mainPageDuckOldPrice = driver.findElement(oldPriceLocator).getText();
        String mainPageDuckOldPriceColor = driver.findElement(oldPriceLocator).getCssValue("color");
        String mainPageDuckOldPriceSize = driver.findElement(oldPriceLocator).getCssValue("font-size");
        String mainPageDuckOldPriceFormat = driver.findElement(oldPriceLocator).getCssValue("text-decoration-line");
        String mainPageDuckNewPrice = driver.findElement(newPriceLocator).getText();
        String mainPageDuckNewPriceColor = driver.findElement(newPriceLocator).getCssValue("color");
        String mainPageDuckNewPriceSize = driver.findElement(newPriceLocator).getCssValue("font-size");
        String mainPageDuckNewPriceFormat = driver.findElement(newPriceLocator).getCssValue("font-weight");

        return new DuckObject(mainPageTitle,
                mainPageDuckOldPrice,
                mainPageDuckOldPriceColor,
                mainPageDuckOldPriceSize,
                mainPageDuckOldPriceFormat,
                mainPageDuckNewPrice,
                mainPageDuckNewPriceColor,
                mainPageDuckNewPriceSize,
                mainPageDuckNewPriceFormat);
    }

    private void compareDucks(DuckObject duck1, DuckObject duck2) {
        boolean isTitleSame = duck1.getTitle().equals(duck2.getTitle());
        boolean isOldPriceSame = duck1.getOldPrice().equals(duck2.getOldPrice());
        boolean isNewPriceSame = duck1.getNewPrice().equals(duck2.getNewPrice());
        System.out.println("-> сравниваем название " + duck1.getTitle() + " c " + duck2.getTitle() + ": " + isTitleSame);
        System.out.println("-> сравниваем старую цену " + duck1.getOldPrice() + " c " + duck2.getOldPrice() + ": " + isOldPriceSame);
        System.out.println("-> сравниваем новую цену " + duck1.getNewPrice() + " c " + duck2.getNewPrice() + ": " + isNewPriceSame);
    }

    private void compareTextColor(DuckObject duck){
        System.out.println("-> Проверка цвета:");
        String oldPriceColor = duck.getOldPriceColor();
        System.out.println("-> Цвет старой цены: "+ oldPriceColor);
        String oldColorCodes = oldPriceColor.substring(4, oldPriceColor.length()-1);
        List<String> oldColorCodesList = Arrays.asList(oldColorCodes.split(","));
        if(isColorGray(oldColorCodesList)){
            System.out.println("-> Совпадает с серым цветом");
        } else{
            System.out.println("-> Не совпадает с серым цветом");
        }
        String newPriceColor = duck.getNewPriceColor();
        System.out.println("-> Цвет новой цены: "+ newPriceColor);
        String newColorCodes = newPriceColor.substring(4, newPriceColor.length()-1);
        List<String> newColorCodesList = Arrays.asList(newColorCodes.split(","));
        if(isColorRed(newColorCodesList)){
            System.out.println("-> Совпадает с красным цветом");
        } else{
            System.out.println("-> Не совпадает с красным цветом");
        }
    }

    private boolean isColorGray(List<String> list){
        int r = Integer.parseInt(list.get(0).trim());
        int g = Integer.parseInt(list.get(1).trim());
        int b = Integer.parseInt(list.get(2).trim());
        final boolean isRWithinRange = 40 <= r && r <= 200;
        final boolean isGWithinRange = r - 3 <= g && g <= r + 3;
        final boolean isBWithinRange = r - 3 <= b && b <= r + 3;
        return isRWithinRange && isGWithinRange && isBWithinRange;
    }

    private boolean isColorRed(List<String> list){
        int r = Integer.parseInt(list.get(0).trim());
        int g = Integer.parseInt(list.get(1).trim());
        int b = Integer.parseInt(list.get(2).trim());
        return r>=150 && g<=30 && b<=30;
    }

    private void compareTextSize(DuckObject duck){
        System.out.println("-> Проверка размера текста: размер старой цены: "+duck.getOldPriceSize()+"; размер новой цены: "+duck.getNewPriceSize());
        if(duck.getOldPriceSize()<duck.getNewPriceSize()){
            System.out.println("-> Размер новой цены больше размера старой цены");
        } else{
            System.out.println("-> Размер новой цены меньше размера старой цены");
        }
    }
}
