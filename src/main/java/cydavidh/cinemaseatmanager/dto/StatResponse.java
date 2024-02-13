package cydavidh.cinemaseatmanager.dto;

public class StatResponse {
    private int income;
    private int available;
    private int purchased;

    public StatResponse(int income, int available, int purchased) {
        this.income = income;
        this.available = available;
        this.purchased = purchased;
    }

    public int getIncome() {
        return income;
    }

    public int getAvailable() {
        return available;
    }

    public int getPurchased() {
        return purchased;
    }
}
