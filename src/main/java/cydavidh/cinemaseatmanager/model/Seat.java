package cydavidh.cinemaseatmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {
    private int row;
    private int column;

    private int price;
    @JsonIgnore
    private boolean available;

    public Seat() {
    }

    public Seat(int row, int col) {
        this.row = row;
        this.column = col;
        this.price = row < 4 ? 10 : 8;
        available = true;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}