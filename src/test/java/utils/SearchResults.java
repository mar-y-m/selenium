package utils;

public class SearchResults {
    public  String title;
    public  String price;
    public  String address;
    public  String info;

    public SearchResults(){

    }

    public SearchResults(String passedTitle, String passedPrice, String passedAddress, String passedInfo){
        this.title = passedTitle;
        this.price = passedPrice;
        this.address = passedAddress;
        this.info = passedInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
