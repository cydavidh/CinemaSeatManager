package cydavidh.cinemaseatmanager.dto;
import java.util.UUID;

public class PurchaseResponse {
    private UUID token;
    private SeatResponse ticket;

    public PurchaseResponse(UUID token, SeatResponse ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public SeatResponse getTicket() {
        return ticket;
    }

    public void setTicket(SeatResponse ticket) {
        this.ticket = ticket;
    }
}
