package club.vasilis.coolq.jx3.domain;

/**
 * @author Vasilis
 * @date 2019/5/10 -21:47
 */

public class Dd373 {
    private String id;

    private String trade;
    private int number;
    private String unit;
    private double amount;
    private double singleprice;
    private double price;

    public Dd373() {
    }

    public Dd373(String id, String trade, int number, String unit, double amount, double singleprice, double price) {
        this.id = id;
        this.trade = trade;
        this.number = number;
        this.unit = unit;
        this.amount = amount;
        this.singleprice = singleprice;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getSingleprice() {
        return singleprice;
    }

    public void setSingleprice(double singleprice) {
        this.singleprice = singleprice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dd373{" +
                "id='" + id + '\'' +
                ", trade='" + trade + '\'' +
                ", number=" + number +
                ", unit='" + unit + '\'' +
                ", amount=" + amount +
                ", singleprice=" + singleprice +
                ", price=" + price +
                '}';
    }
}
