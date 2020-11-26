package cian.utils;

import java.util.Arrays;
import java.util.List;

public class FilterInputValues {
    public static String getFilterTab() {
        return filterTab;
    }

    public static void setFilterTab(String filterTab) {
        FilterInputValues.filterTab = filterTab;
    }

    public static List<String> getOfferTypes() {
        return offerTypes;
    }

    public static void setOfferTypes(String offerTypesString) {
        FilterInputValues.offerTypes = Arrays.asList(offerTypesString.split(","));
    }

    public static List<String> getRoomsCount() {
        return roomsCount;
    }

    public static void setRoomsCount(String roomsCountString) {

        FilterInputValues.roomsCount = Arrays.asList(roomsCountString.split(","));
    }

    public static String getPriceFrom() {
        return priceFrom;
    }

    public static void setPriceFrom(String priceFrom) {
        FilterInputValues.priceFrom = priceFrom;
    }

    public static String getPriceTo() {
        return priceTo;
    }

    public static void setPriceTo(String priceTo) {
        FilterInputValues.priceTo = priceTo;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        FilterInputValues.city = city;
    }

    public static String filterTab;
    public static List<String> offerTypes;
    public static List<String> roomsCount;
    public static String priceFrom;
    public static String priceTo;
    public static String city;
}
