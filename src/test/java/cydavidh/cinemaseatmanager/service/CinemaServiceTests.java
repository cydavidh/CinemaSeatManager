package cydavidh.cinemaseatmanager.service;

import cydavidh.cinemaseatmanager.dto.CinemaResponse;
import cydavidh.cinemaseatmanager.dto.PurchaseResponse;
import cydavidh.cinemaseatmanager.dto.ReturnResponse;
import cydavidh.cinemaseatmanager.dto.StatResponse;
import cydavidh.cinemaseatmanager.exception.OutOfBoundsException;
import cydavidh.cinemaseatmanager.exception.TicketAlreadyPurchasedException;
import cydavidh.cinemaseatmanager.exception.WrongTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CinemaServiceTests {
    private CinemaService cinemaService;

    @BeforeEach
    public void setup() {
        cinemaService = new CinemaService(3, 3);
    }

    @Test
    public void testGetAvailableSeatsInitially() {
        CinemaResponse cinemaResponse = cinemaService.getAvailableSeats();
        assertNotNull(cinemaResponse);
        assertEquals(9, cinemaResponse.getSeats().size());
    }

    @Test
    public void testPurchaseSeatOutOfBounds() {
        assertThrows(OutOfBoundsException.class, () -> cinemaService.purchaseSeat(-1, 4));
    }

    @Test
    public void testPurchaseSeatAlreadyPurchased() {
        cinemaService.purchaseSeat(2, 2);
        assertThrows(TicketAlreadyPurchasedException.class, () -> cinemaService.purchaseSeat(2, 2));
    }

    @Test
    public void testPurchaseSeatSuccessfully() {
        assertDoesNotThrow(() -> {
            PurchaseResponse purchaseResponse = cinemaService.purchaseSeat(1, 1); // Rows and cols are 0-indexed
            assertNotNull(purchaseResponse);
            assertFalse(cinemaService.getAvailableSeats().getSeats().stream().anyMatch(seat -> seat.getRow() == 2 && seat.getColumn() == 2));
        });
    }

    @Test
    public void testReturnSeatWithWrongToken() {
        UUID wrongToken = UUID.randomUUID();
        assertThrows(WrongTokenException.class, () -> cinemaService.returnSeat(wrongToken)); // Use an invalid token
    }

    @Test
    public void testReturnSeatSuccessfully() {
        PurchaseResponse purchaseResponse = cinemaService.purchaseSeat(0, 0); // Purchase a seat
        UUID token = purchaseResponse.getToken();
        assertDoesNotThrow(() -> {
            ReturnResponse returnResponse = cinemaService.returnSeat(token); // Return the seat
            assertNotNull(returnResponse);
            assertTrue(cinemaService.getAvailableSeats().getSeats().stream().anyMatch(seat -> seat.getRow() == 1 && seat.getColumn() == 1));
        });
    }

    @Test
    public void testGetStatsAfterOperations() {
        cinemaService.purchaseSeat(1, 1); // Purchase a seat
        StatResponse stats = cinemaService.getStats();
        assertNotNull(stats);
        assertEquals(8, stats.getAvailable()); // One seat purchased
        assertEquals(1, stats.getPurchased());
        // Assuming seat price is constant; adjust the assertion below as necessary
        assertTrue(stats.getIncome() > 0);
    }

}
