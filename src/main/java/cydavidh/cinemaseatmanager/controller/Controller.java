package cydavidh.cinemaseatmanager.controller;

import cydavidh.cinemaseatmanager.dto.CinemaResponse;
import cydavidh.cinemaseatmanager.dto.PurchaseResponse;
import cydavidh.cinemaseatmanager.dto.ReturnResponse;
import cydavidh.cinemaseatmanager.dto.TokenRequest;
import cydavidh.cinemaseatmanager.exception.IncorrectPasswordException;
import cydavidh.cinemaseatmanager.model.Cinema;
import cydavidh.cinemaseatmanager.model.Seat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    private Cinema cinema;

    public Controller() {
        cinema = new Cinema(9, 9);
    }

    @GetMapping("/seats")
    public CinemaResponse getSeats() {
        return cinema.getSeats();
    }

    @PostMapping("/purchase")
    public PurchaseResponse puchaseSeat(@RequestBody Seat inputSeat) {
        return cinema.purchaseSeat(inputSeat.getRow() - 1, inputSeat.getColumn() - 1);
    }

    @PostMapping("/return")
    public ReturnResponse returnSeat(@RequestBody TokenRequest tokenRequest) {
        return cinema.returnSeat(tokenRequest.getToken());
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(required = false) String password) {
        if (password == null || !"super_secret".equals(password)) {
            throw new IncorrectPasswordException("The password is wrong!");
        }
        return ResponseEntity.ok(cinema.getStats());
    }
}