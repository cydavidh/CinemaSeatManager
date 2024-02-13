package cydavidh.cinemaseatmanager.model;

import cydavidh.cinemaseatmanager.dto.*;
import cydavidh.cinemaseatmanager.exception.OutOfBoundsException;
import cydavidh.cinemaseatmanager.exception.TicketAlreadyPurchasedException;
import cydavidh.cinemaseatmanager.exception.WrongTokenException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Cinema {
    private int rows;
    private int cols;
    private int income;
    private Seat[][] seats;

    private Map<UUID, Seat> tokens;

    public Cinema(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.income = 0;
        seats = new Seat[rows][cols];
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

    public CinemaResponse getSeats() {
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

    public PurchaseResponse purchaseSeat(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new OutOfBoundsException("The number of a row or a column is out of bounds!");
        }
        Seat seatToPurchase = seats[row][col];
        if (!seatToPurchase.isAvailable()) {
            throw new TicketAlreadyPurchasedException("The ticket has been already purchased!");
        }
        seatToPurchase.setAvailable(false);
        UUID uuid = UUID.randomUUID();
        tokens.put(uuid, seatToPurchase);
        income += seatToPurchase.getPrice();
        return new PurchaseResponse(uuid, new SeatResponse(row + 1, col + 1, seatToPurchase.getPrice()));
    }


    public ReturnResponse returnSeat(UUID uuid) {
        Seat seatToRefund = tokens.get(uuid);
        if (seatToRefund == null) {
            throw new WrongTokenException("Wrong token!");
        }
        seatToRefund.setAvailable(true);
        tokens.remove(uuid);
        SeatResponse seatResponse = new SeatResponse(seatToRefund.getRow() + 1, seatToRefund.getColumn() + 1, seatToRefund.getPrice());
        ReturnResponse returnResponse = new ReturnResponse(seatResponse);
        income -= seatToRefund.getPrice();
        return returnResponse;
    }


    public StatResponse getStats() {
        int available = rows*cols - tokens.size();
        int purchased = tokens.size();
        return new StatResponse(income, available, purchased);
    }
}