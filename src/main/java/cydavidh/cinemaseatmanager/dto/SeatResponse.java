package cydavidh.cinemaseatmanager.dto;

public class SeatResponse {
    private int row;
    private int column;
    private int price;

    public SeatResponse(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    // Getters and Setters

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }
}
