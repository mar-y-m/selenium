package hw;

import cian.TestBase;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.openqa.selenium.By.xpath;

public class MySortHW extends TestBase {

    @Test
    public void test() {
        final By countriesButtonLocator = xpath("//li[@data-code='countries']/a");
        final By countryNameLinksLocator = xpath("//tr/td[5]/a");
        final By countryZonesLocator = xpath("//tr/td[6]");
        final By zonesHeaderLocator = xpath("//h2[text()='Zones']");
        final By zonesNamesLocator = xpath("//td[3]/input");

        driver.get("http://localhost:9003/litecart/admin/");

        loginAsAdmin();

        Assert.assertTrue(isElementPresent(countriesButtonLocator));
        driver.findElement(countriesButtonLocator).click();

        Assert.assertTrue(isElementPresent(countryNameLinksLocator));
        List<WebElement> countriesLinksList = driver.findElements(countryNameLinksLocator);
        List<String> countriesNamesList = getTextList(countriesLinksList);
        checkAlphabeticalOrder(countriesNamesList);

        Assert.assertTrue(isElementPresent(countryZonesLocator));
        List<String> countriesWithZones = findCountiesWithZones(countryZonesLocator, countryNameLinksLocator);

        if (!countriesWithZones.isEmpty()) {
            checkCountryZonesOrder(countryNameLinksLocator, zonesHeaderLocator, zonesNamesLocator, countriesWithZones);
        }
    }


    private List<String> getTextList(List<WebElement> elementsList) {
        return elementsList.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    private List<String> getValueList(List<WebElement> elementsList) {
        return elementsList.stream()
                .map(e -> e.getAttribute("value"))
                .collect(Collectors.toList());
    }

    private void checkAlphabeticalOrder(List<String> countriesNames) {
        Iterator<String> iterator = countriesNames.listIterator();
        String current, previous = iterator.next();
        while (iterator.hasNext()) {
            current = iterator.next();
            if (previous.compareTo(current) >= 0) {
                System.out.println("--> comparing values: value 1 is " + previous + " value 2 is " + current + ". The sorting is not correct.");
            } else {
                System.out.println("--> comparing values: value 1 is " + previous + " value 2 is " + current + ". The sorting is correct.");
            }
            previous = current;
        }
    }

    private List<String> findCountiesWithZones(By zoneCountLocator, By countryLinkLocator) {
        List<WebElement> countriesWithZones = new ArrayList<>();

        int countries = driver.findElements(zoneCountLocator).size();
        for (int i = 0; i < countries; i++) {
            WebElement currentZones = driver.findElements(zoneCountLocator).get(i);
            int currentZoneCount = Integer.parseInt(currentZones.getText());
            if (currentZoneCount > 0) {
                WebElement countryWithZones = driver.findElements(countryLinkLocator).get(i);
                countriesWithZones.add(countryWithZones);
                System.out.println(countryWithZones.getText() + currentZoneCount);
            }
        }
        return getTextList(countriesWithZones);
    }

    private void checkCountryZonesOrder(By countryNameLinksLocator, By zonesHeaderLocator, By zonesNamesLocator, List<String> countriesWithZones) {
        for (String currentCountyName : countriesWithZones) {
            System.out.println("Current country with zones is: " + currentCountyName);
            WebElement currentCountryLink = driver.findElement(By.ByLinkText.linkText(currentCountyName));
            currentCountryLink.click();

            Assert.assertTrue(isElementPresent(zonesHeaderLocator));

            List<WebElement> zonesList = driver.findElements(zonesNamesLocator);
            List<String> zoneNamesList = getValueList(zonesList);
            checkAlphabeticalOrder(zoneNamesList);

            driver.navigate().back();
            Assert.assertTrue(isElementPresent(countryNameLinksLocator));
        }
    }

    private void loginAsAdmin() {
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).submit();
    }
}
