package cydavidh.cinemaseatmanager.exception;

public class TicketAlreadyPurchasedException extends RuntimeException {
    public TicketAlreadyPurchasedException(String message) {
        super(message);
    }
}
