package cydavidh.cinemaseatmanager.dto;

import java.util.List;

public class CinemaResponse {
    private int rows;
    private int columns;
    private List<SeatResponse> seats;

    public CinemaResponse(int rows, int columns, List<SeatResponse> seats) {
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }

    // Getters and Setters

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<SeatResponse> getSeats() {
        return seats;
    }
}