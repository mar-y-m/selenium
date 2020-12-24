package hw.comparehw;

public class DuckObject {
    private String title;

    private String oldPrice;
    private String oldPriceColor;
    private double oldPriceSize;
    private boolean isOldPriceTextLineThrough;

    private String newPrice;
    private String newPriceColor;
    private double newPriceSize;
    private boolean isNewPriceTextBold;

    public DuckObject(String title, String oldPrice, String oldPriceColor, String oldPriceSize, String oldPriceTextFormat, String newPrice, String newPriceColor, String newPriceSize, String newPriceTextFormat) {
        this.title = title;
        this.oldPrice = oldPrice;
        this.oldPriceColor = oldPriceColor;
        this.oldPriceSize = stringToDouble(oldPriceSize);
        this.isOldPriceTextLineThrough = isTextLineThrough(oldPriceTextFormat);
        this.newPrice = newPrice;
        this.newPriceColor = newPriceColor;
        this.newPriceSize = stringToDouble(newPriceSize);
        this.isNewPriceTextBold = isTextBold(newPriceTextFormat);
    }

    private double stringToDouble(String s) {
        return Double.parseDouble(s.substring(0, s.length() - 2));
    }

    private boolean isTextBold(String s) {
        int fontWeight = Integer.parseInt(s);
        return fontWeight >= 700;
    }

    private boolean isTextLineThrough(String s) {
        return s.equals("line-through");
    }

    public String getTitle() {
        return title;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public String getOldPriceColor() {
        return oldPriceColor;
    }

    public double getOldPriceSize() {
        return oldPriceSize;
    }

    public boolean isOldPriceTextLineThrough() {
        return isOldPriceTextLineThrough;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public String getNewPriceColor() {
        return newPriceColor;
    }

    public double getNewPriceSize() {
        return newPriceSize;
    }

    public boolean isNewPriceTextBold() {
        return isNewPriceTextBold;
    }

    @Override
    public String toString() {
        return "DuckObject{" +
                "title='" + title + '\'' +
                ", oldPrice='" + oldPrice + '\'' +
                ", oldPriceColor='" + oldPriceColor + '\'' +
                ", oldPriceSize=" + oldPriceSize +
                ", isOldPriceTextLineThrough='" + isOldPriceTextLineThrough + '\'' +
                ", newPrice='" + newPrice + '\'' +
                ", newPriceColor='" + newPriceColor + '\'' +
                ", newPriceSize=" + newPriceSize +
                ", isNewPriceTextBold='" + isNewPriceTextBold + '\'' +
                '}';
    }

}
