package cydavidh.cinemaseatmanager.controller;

import cydavidh.cinemaseatmanager.dto.CinemaResponse;
import cydavidh.cinemaseatmanager.dto.PurchaseResponse;
import cydavidh.cinemaseatmanager.dto.ReturnResponse;
import cydavidh.cinemaseatmanager.dto.TokenRequest;
import cydavidh.cinemaseatmanager.exception.IncorrectPasswordException;
import cydavidh.cinemaseatmanager.model.Seat;
import cydavidh.cinemaseatmanager.service.CinemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class Controller {
    private final CinemaService cinemaService;

    public Controller() {
        this.cinemaService = new CinemaService(9, 9);
    }

    @GetMapping("/seats")
    public CinemaResponse getSeats() {
        return cinemaService.getAvailableSeats();
    }

    @PostMapping("/purchase")
    public PurchaseResponse purchaseSeat(@RequestBody Seat inputSeat) {
        return cinemaService.purchaseSeat(inputSeat.getRow() - 1, inputSeat.getColumn() - 1);
    }

    @PostMapping("/return")
    public ReturnResponse returnSeat(@RequestBody TokenRequest tokenRequest) {
        return cinemaService.returnSeat(tokenRequest.getToken());
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(required = false) String password) {
        if (password == null || !"super_secret".equals(password)) {
            throw new IncorrectPasswordException("The password is wrong!");
        }
        return ResponseEntity.ok(cinemaService.getStats());
    }
}
