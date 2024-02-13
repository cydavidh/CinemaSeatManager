package cydavidh.cinemaseatmanager;

import cydavidh.cinemaseatmanager.dto.CinemaResponse;
import cydavidh.cinemaseatmanager.dto.StatResponse;
import cydavidh.cinemaseatmanager.dto.PurchaseResponse;
import cydavidh.cinemaseatmanager.dto.ReturnResponse;
import cydavidh.cinemaseatmanager.model.Cinema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CinemaTests {
    private Cinema cinema;

    @BeforeEach
    void setUp() {
        cinema = new Cinema(10, 10);
    }

    @Test
    void testGetSeats() {
        CinemaResponse response = cinema.getSeats();
        assertEquals(10, response.getRows());
        assertEquals(10, response.getColumns());
        assertEquals(100, response.getSeats().size());
    }

    @Test
    void testPurchaseSeat() {
        PurchaseResponse response = cinema.purchaseSeat(0, 0);
        assertNotNull(response.getToken());
        assertEquals(1, response.getTicket().getRow());
        assertEquals(1, response.getTicket().getColumn());
    }

    @Test
    void testReturnSeat() {
        PurchaseResponse purchaseResponse = cinema.purchaseSeat(0, 0);
        ReturnResponse returnResponse = cinema.returnSeat(purchaseResponse.getToken());
        assertEquals(1, returnResponse.getTicket().getRow());
        assertEquals(1, returnResponse.getTicket().getColumn());
    }

    @Test
    void testGetStats() {
        cinema.purchaseSeat(0, 0);
        StatResponse stats = cinema.getStats();
        assertEquals(10, stats.getIncome());
        assertEquals(99, stats.getAvailable());
        assertEquals(1, stats.getPurchased());
    }
}
