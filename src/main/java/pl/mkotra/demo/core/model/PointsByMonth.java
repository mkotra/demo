package pl.mkotra.demo.core.model;

public class PointsByMonth {

    private String customerId;
    private int month;
    private int points;

    public PointsByMonth() {

    }

    public PointsByMonth(String customerId, int month, int points) {
        this.customerId = customerId;
        this.month = month;
        this.points = points;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
