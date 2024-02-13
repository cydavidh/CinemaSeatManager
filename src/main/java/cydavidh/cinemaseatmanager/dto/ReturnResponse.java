package cydavidh.cinemaseatmanager.dto;

public class ReturnResponse {
    private SeatResponse ticket;

    public ReturnResponse(SeatResponse ticket) {
        this.ticket = ticket;
    }

    public SeatResponse getTicket() {
        return ticket;
    }

    public void setTicket(SeatResponse ticket) {
        this.ticket = ticket;
    }
}
