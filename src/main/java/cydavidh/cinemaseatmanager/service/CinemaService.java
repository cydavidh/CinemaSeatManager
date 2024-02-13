package cydavidh.cinemaseatmanager.service;

import cydavidh.cinemaseatmanager.dto.*;
import cydavidh.cinemaseatmanager.exception.OutOfBoundsException;
import cydavidh.cinemaseatmanager.exception.TicketAlreadyPurchasedException;
import cydavidh.cinemaseatmanager.exception.WrongTokenException;
import cydavidh.cinemaseatmanager.model.Cinema;
import cydavidh.cinemaseatmanager.model.Seat;

import java.util.UUID;

public class CinemaService {
    private final Cinema cinema;

    public CinemaService(int rows, int cols) {
        this.cinema = new Cinema(rows, cols);
    }

    public synchronized CinemaResponse getAvailableSeats() {
        return cinema.getAvailableSeats();
    }

    public synchronized PurchaseResponse purchaseSeat(int row, int col) {
        if (row < 0 || row >= cinema.getRows() || col < 0 || col >= cinema.getCols()) {
            throw new OutOfBoundsException("The number of a row or a column is out of bounds!");
        }
        Seat seatToPurchase = cinema.getSeat(row, col);
        if (!seatToPurchase.isAvailable()) {
            throw new TicketAlreadyPurchasedException("The ticket has been already purchased!");
        }
        seatToPurchase.setAvailable(false);
        UUID uuid = UUID.randomUUID();
        cinema.getTokens().put(uuid, seatToPurchase);
        cinema.increaseIncome(seatToPurchase.getPrice());
        return new PurchaseResponse(uuid, new SeatResponse(row + 1, col + 1, seatToPurchase.getPrice()));
    }

    public synchronized ReturnResponse returnSeat(UUID uuid) {
        Seat seatToRefund = cinema.getTokens().get(uuid);
        if (seatToRefund == null) {
            throw new WrongTokenException("Wrong token!");
        }
        seatToRefund.setAvailable(true);
        cinema.getTokens().remove(uuid);
        SeatResponse seatResponse = new SeatResponse(seatToRefund.getRow() + 1, seatToRefund.getColumn() + 1, seatToRefund.getPrice());
        ReturnResponse returnResponse = new ReturnResponse(seatResponse);
        cinema.decreaseIncome(seatToRefund.getPrice());
        return returnResponse;
    }

    public synchronized StatResponse getStats() {
        int available = cinema.getRows() * cinema.getCols() - cinema.getTokens().size();
        int purchased = cinema.getTokens().size();
        return new StatResponse(cinema.getIncome(), available, purchased);
    }
}
