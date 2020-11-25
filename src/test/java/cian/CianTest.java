package cian;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ExcelUtils;
import utils.FilterInputValues;
import utils.SearchResults;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.By.*;

public class CianTest extends TestBase {

    protected ExcelUtils excel = new ExcelUtils();

    @Test
    public void test() {
        excel.init("Book1.xlsx", "Sheet1");
        generateFilterValues(1);

        driver.get("https://voronezh.cian.ru/");

        selectFilterTab(FilterInputValues.getFilterTab());
        selectOfferType(FilterInputValues.getOfferTypes());
        selectRoomsCount(FilterInputValues.getRoomsCount());
        selectPriceRange(FilterInputValues.getPriceFrom(), FilterInputValues.getPriceTo());
        selectCity(FilterInputValues.getCity());

        driver.findElement(ByXPath.xpath("//div[@data-name='FiltersBlock']//a[@data-mark='FiltersSearchButton']")).click();

        String pathToFile = "C:\\Users\\mminaeva\\IdeaProjects\\selenium\\src\\test\\resources\\Results1.xlsx";
        List<SearchResults> resultsList = getOffersInfo();

        excel.writeExcel(resultsList, pathToFile);
    }


    private void selectFilterTab(String requiredType) {
        WebElement activeTypeFilterLink = driver.findElement(xpath("//ul[@data-name='FiltersTabs']/li[contains(@class,'active')]/a"));
        String activeLinkName = activeTypeFilterLink.getText();
        if (!activeLinkName.equals(requiredType)) {
            WebElement correctType = driver.findElement(By.ByLinkText.linkText(requiredType));
            correctType.click();
        }
    }

    private void selectOfferType(List<String> requireOfferTypeList) {
        WebElement offerTypeFilterButton = driver.findElement(By.xpath("//button[@class='_025a50318d--button--2oXjq']"));
        offerTypeFilterButton.click();

        By pathToOfferTypeDropdown = By.xpath("//div[@class='_025a50318d--dropdown--3wfz1']");
        Assert.assertTrue(isElementPresent(pathToOfferTypeDropdown));

        int offerTypeOptionsCount = driver.findElements(By.xpath("//ul[@class='_025a50318d--list-inner--3S0Cv']/li")).size();
        System.out.println(offerTypeOptionsCount);
        for (int i = 0; i < offerTypeOptionsCount; i++) {
            WebElement currentOfferTypeCheckboxLabel = driver.findElements(By.xpath("//ul[@class='_025a50318d--list-inner--3S0Cv']/li//span[@class='_025a50318d--label--2B10u']")).get(i);
            String currentOfferTypeCheckboxLabelText = currentOfferTypeCheckboxLabel.getText();
            System.out.println(currentOfferTypeCheckboxLabelText);

            WebElement currentOfferTypeCheckbox = driver.findElement(By.xpath("//span[text()='" + currentOfferTypeCheckboxLabelText + "']//parent::span[contains(@class, 'checkbox')]"));

            if (requireOfferTypeList.contains(currentOfferTypeCheckboxLabelText)) {
                if (!currentOfferTypeCheckbox.getAttribute("class").contains("checked")) {
                    currentOfferTypeCheckbox.click();
                    System.out.println("Checkbox " + currentOfferTypeCheckboxLabelText + " was ticked by test");
                } else {
                    System.out.println("Checkbox " + currentOfferTypeCheckboxLabelText + " is already ticked");
                }
            } else {
                if (currentOfferTypeCheckbox.getAttribute("class").contains("checked")) {
                    currentOfferTypeCheckbox.click();
                    System.out.println("Checkbox " + currentOfferTypeCheckboxLabelText + " was unticked by test");
                } else {
                    System.out.println("Checkbox " + currentOfferTypeCheckboxLabelText + " is already unticked");
                }
            }
        }
    }

    private void selectRoomsCount(List<String> listOfRoomsCountValues) {
        WebElement roomsCountFilterButton = driver.findElement(By.xpath("//button[@title='1, 2 комн.']"));
        roomsCountFilterButton.click();

        By pathToRoomsCountDropdown = By.xpath("//div[@class='_025a50318d--dropdown--11qNB']");
        Assert.assertTrue(isElementPresent(pathToRoomsCountDropdown));

        int roomsCountButtonsNumber = driver.findElements(By.xpath("//ul[@class='_025a50318d--list--3ybsu']/li")).size();
        for (int i = 0; i < roomsCountButtonsNumber; i++) {
            WebElement currentButton = driver.findElements(By.xpath("//ul[@class='_025a50318d--list--3ybsu']/li/button")).get(i);

            if (listOfRoomsCountValues.contains(currentButton.getText())) {

                if (!currentButton.getAttribute("class").contains("active")) {
                    currentButton.click();
                    Assert.assertTrue(currentButton.getAttribute("class").contains("active"));
                    System.out.println("Button " + currentButton.getText() + " was selected by test");
                } else {
                    System.out.println("Button " + currentButton.getText() + " is already selected");
                }
            } else {
                if (currentButton.getAttribute("class").contains("active")) {
                    currentButton.click();
                    Assert.assertFalse(currentButton.getAttribute("class").contains("active"));
                    System.out.println("Button " + currentButton.getText() + " was deselected by test");
                } else {
                    System.out.println("Button " + currentButton.getText() + " is already not selected");
                }
            }
        }

    }

    private void selectPriceRange(String from, String to) {
        driver.findElement(xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']/button")).click();
        Assert.assertTrue(isElementPresent(xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']/div[contains(@class,'dropdown')]")));
        driver.findElement(By.xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']//input[@placeholder='от']")).sendKeys(from);
        driver.findElement(By.xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']//input[@placeholder='до']")).sendKeys(to);
    }

    private void selectCity(String city) {
        WebElement cityInputField = driver.findElement(By.xpath("//div[@data-name='Filters']//div[@data-mark='FilterGeo']//input"));
        cityInputField.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(xpath("//div[@data-name='Filters']//div[@data-mark='FilterPrice']/div[contains(@class,'dropdown')]")));
        cityInputField.sendKeys(city);
        Assert.assertTrue(isElementPresent(xpath("//div[@data-name='SuggestionPopup']")));
        driver.findElements(xpath("//div[@data-name='SuggestionPopup']//span[@title='Воронеж']")).get(0).click();
    }

    private List<SearchResults> getOffersInfo() {
        Assert.assertTrue(isElementPresent(By.xpath("//div[@data-name='Offers']")));

        List<SearchResults> searchResultsList = new ArrayList<>();

        int numberOfCards = driver.findElements(By.xpath("//div[@data-name='Offers']/article")).size();

        if (numberOfCards > 0) {
            for (int i = 0; i < numberOfCards; i++) {
                WebElement currentCardTitle = driver.findElements(By.xpath("//div[@data-name='Offers']//div[@data-name='TitleComponent']")).get(i);
                String currentCardTitleText = currentCardTitle.getText();

                WebElement currentCardPrice = driver.findElements(By.xpath("//div[@data-name='Offers']//span[@data-mark='MainPrice']")).get(i);
                String currentCardPriceText = currentCardPrice.getText();

                WebElement currentCardAddress = driver.findElements(By.xpath("//div[@data-name='Offers']//div[contains(@class,'_93444fe79c--labels--1J6M3')]")).get(i);
                String currentCardAddressText = currentCardAddress.getText();

                WebElement currentCardInfo = driver.findElements(By.xpath("//div[@data-name='Offers']//div[contains(@class,'description')]/p")).get(i);
                String currentCardInfoText = currentCardInfo.getText();

                SearchResults searchResultsIndx = new SearchResults(currentCardTitleText, currentCardPriceText, currentCardAddressText, currentCardInfoText);
                searchResultsList.add(searchResultsIndx);

            }
        }
        return searchResultsList;
    }

    private void generateFilterValues(int rowNr) {
        XSSFRow currentRow = excel.getRowData(rowNr);
        FilterInputValues.setFilterTab(currentRow.getCell(0).getStringCellValue());
        FilterInputValues.setOfferTypes(currentRow.getCell(1).getStringCellValue());
        FilterInputValues.setRoomsCount(currentRow.getCell(2).getStringCellValue());
        FilterInputValues.setPriceFrom(currentRow.getCell(3).getStringCellValue());
        FilterInputValues.setPriceTo(currentRow.getCell(4).getStringCellValue());
        FilterInputValues.setCity(currentRow.getCell(5).getStringCellValue());

    }

}
