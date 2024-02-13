package cydavidh.cinemaseatmanager.model;

import cydavidh.cinemaseatmanager.dto.CinemaResponse;
import cydavidh.cinemaseatmanager.dto.SeatResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Cinema {
    private final int rows;
    private final int cols;
    private volatile int income;
    private final Seat[][] seats;

    private final Map<UUID, Seat> tokens;

    public Cinema(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.income = 0;
        this.seats = new Seat[rows][cols];
        this.tokens = new ConcurrentHashMap<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getIncome() {
        synchronized (this) {
            return income;
        }
    }

    public void increaseIncome(int amount) {
        synchronized (this) {
            this.income += amount;
        }
    }

    public void decreaseIncome(int amount) {
        synchronized (this) {
            this.income -= amount;
        }
    }

    public Map<UUID, Seat> getTokens() {
        return tokens;
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public Seat getSeat(int row, int col) {
        return seats[row][col];
    }

    public CinemaResponse getAvailableSeats() {
        List<SeatResponse> availableSeats = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Seat seat = seats[i][j];
                if (seat.isAvailable()) {
                    availableSeats.add(new SeatResponse(seat.getRow() + 1, seat.getColumn() + 1, seat.getPrice()));
                }
            }
        }
        return new CinemaResponse(rows, cols, availableSeats);
    }

    // Optionally, include helper methods for direct seat access if needed by the service layer,
    // but ensure these are used in a thread-safe manner (possibly managed within the service layer).
}
