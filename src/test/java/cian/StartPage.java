package cian;

import org.openqa.selenium.*;

import static org.openqa.selenium.By.xpath;

public class StartPage extends TestBase {

    public static final By activeTypeFilterLinkLocator = xpath("//ul[@data-name='FiltersTabs']/li[contains(@class,'active')]/a");

    public static final By offerTypeFilterButtonLocator = xpath("//button[@class='_025a50318d--button--2oXjq']");
    public static final By offerTypeDropdownLocator = xpath("//div[@class='_025a50318d--dropdown--3wfz1']");
    public static final By offerTypeOptionsLocator = xpath("//ul[@class='_025a50318d--list-inner--3S0Cv']/li");
    public static final By offerTypeCheckboxLabelLocator = xpath("//ul[@class='_025a50318d--list-inner--3S0Cv']/li//span[@class='_025a50318d--label--2B10u']");

    public static final By roomsCountFilterButtonLocator = xpath("//button[@title='1, 2 комн.']");
    public static final By roomsCountDropdownLocator = xpath("//div[@class='_025a50318d--dropdown--11qNB']");
    public static final By roomsCountButtonsLocator = xpath("//ul[@class='_025a50318d--list--3ybsu']/li");
    public static final By roomsCountButtonLocator = xpath("//ul[@class='_025a50318d--list--3ybsu']/li/button");

    public static final By priceFilterButtonLocator = xpath("//div[@data-mark='FilterPrice']/button");
    public static final By priceFilterDropdownLocator = xpath("//div[@data-mark='FilterPrice']/div[contains(@class,'dropdown')]");
    public static final By priceFilterFromLocator = xpath("//div[@data-mark='FilterPrice']//input[@placeholder='от']");
    public static final By priceFilterToLocator = xpath("//div[@data-mark='FilterPrice']//input[@placeholder='до']");

    public static final By cityInputFieldLocator = xpath("//div[@data-mark='FilterGeo']//input");
    public static final By citySuggestionPopupLocator = xpath("//div[@data-name='SuggestionPopup']");

    public static final By searchButtonLocator = xpath("//div[@data-name='FiltersBlock']//a[@data-mark='FiltersSearchButton']");

    public static final By offersLocator = xpath("//div[@data-name='Offers']");
    public static final By offerCardsLocator = xpath("//div[@data-name='Offers']/article");
    public static final By offerCardTitleLocator = xpath("//div[@data-name='Offers']//div[@data-name='TitleComponent']");
    public static final By offerCardMainPriceLocator = xpath("//div[@data-name='Offers']//span[@data-mark='MainPrice']");
    public static final By offerCardAddressLocator = xpath("//div[@data-name='Offers']//div[contains(@class,'_93444fe79c--labels--1J6M3')]");
    public static final By offerCardInfoLocator = xpath("//div[@data-name='Offers']//div[contains(@class,'description')]/p");

}
