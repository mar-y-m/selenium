package cian;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import cian.utils.ExcelUtils;
import cian.utils.FilterInputValues;
import cian.utils.SearchResults;

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

        driver.findElement(StartPage.searchButtonLocator).click();

        List<SearchResults> resultsList = getOffersInfo();
        String pathToFile = "C:\\Users\\mminaeva\\IdeaProjects\\selenium\\src\\test\\resources\\Results1.xlsx";

        excel.writeExcel(resultsList, pathToFile);
    }


    private void selectFilterTab(String requiredType) {
        System.out.println(requiredType);
        WebElement activeTypeFilterLink = driver.findElement(StartPage.activeTypeFilterLinkLocator);
        String activeLinkName = activeTypeFilterLink.getText();
        if (!activeLinkName.equals(requiredType)) {
            WebElement correctType = driver.findElement(By.ByLinkText.linkText(requiredType));
            correctType.click();
        }
    }

    private void selectOfferType(List<String> requireOfferTypeList) {
        System.out.println(requireOfferTypeList);
        WebElement offerTypeFilterButton = driver.findElement(StartPage.offerTypeFilterButtonLocator);
        offerTypeFilterButton.click();

        Assert.assertTrue(isElementPresent(StartPage.offerTypeDropdownLocator));

        int offerTypeOptionsCount = driver.findElements(StartPage.offerTypeOptionsLocator).size();
        System.out.println(offerTypeOptionsCount);
        for (int i = 0; i < offerTypeOptionsCount; i++) {
            WebElement currentOfferTypeCheckboxLabel = driver.findElements(StartPage.offerTypeCheckboxLabelLocator).get(i);
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
        System.out.println(listOfRoomsCountValues);
        WebElement roomsCountFilterButton = driver.findElement(StartPage.roomsCountFilterButtonLocator);
        roomsCountFilterButton.click();

        Assert.assertTrue(isElementPresent(StartPage.roomsCountDropdownLocator));

        int roomsCountButtonsNumber = driver.findElements(StartPage.roomsCountButtonsLocator).size();
        for (int i = 0; i < roomsCountButtonsNumber; i++) {
            WebElement currentButton = driver.findElements(StartPage.roomsCountButtonLocator).get(i);

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
        System.out.println(from+to);
        driver.findElement(StartPage.priceFilterButtonLocator).click();
        Assert.assertTrue(isElementPresent(StartPage.priceFilterDropdownLocator));
        driver.findElement(StartPage.priceFilterFromLocator).sendKeys(from);
        driver.findElement(StartPage.priceFilterToLocator).sendKeys(to);
    }

    private void selectCity(String city) {
        System.out.println(city);
        WebElement cityInputField = driver.findElement(StartPage.cityInputFieldLocator);
        cityInputField.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(StartPage.priceFilterDropdownLocator));
        cityInputField.sendKeys(city);
        Assert.assertTrue(isElementPresent(StartPage.citySuggestionPopupLocator));
        driver.findElements(xpath("//div[@data-name='SuggestionPopup']//span[@title='"+city+"']")).get(0).click();
    }

    private List<SearchResults> getOffersInfo() {
        Assert.assertTrue(isElementPresent(StartPage.offersLocator));

        List<SearchResults> searchResultsList = new ArrayList<>();

        int numberOfCards = driver.findElements(StartPage.offerCardsLocator).size();

        if (numberOfCards > 0) {
            for (int i = 0; i < numberOfCards; i++) {
                WebElement currentCardTitle = driver.findElements(StartPage.offerCardTitleLocator).get(i);
                String currentCardTitleText = currentCardTitle.getText();

                WebElement currentCardPrice = driver.findElements(StartPage.offerCardMainPriceLocator).get(i);
                String currentCardPriceText = currentCardPrice.getText();

                WebElement currentCardAddress = driver.findElements(StartPage.offerCardAddressLocator).get(i);
                String currentCardAddressText = currentCardAddress.getText();

                WebElement currentCardInfo = driver.findElements(StartPage.offerCardInfoLocator).get(i);
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
