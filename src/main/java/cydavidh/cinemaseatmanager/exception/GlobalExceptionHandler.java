package cydavidh.cinemaseatmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OutOfBoundsException.class)
    public ResponseEntity<CustomErrorMessage> handleOutOfBoundsException(OutOfBoundsException ex) {
        CustomErrorMessage errorMessage = new CustomErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketAlreadyPurchasedException.class)
    public ResponseEntity<CustomErrorMessage> handleTicketAlreadyPurchasedException(TicketAlreadyPurchasedException ex) {
        CustomErrorMessage errorMessage = new CustomErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongTokenException.class)
    public ResponseEntity<CustomErrorMessage> handleWrongTokenException(WrongTokenException ex) {
        CustomErrorMessage errorMessage = new CustomErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<CustomErrorMessage> handleIncorrectPasswordException(IncorrectPasswordException ex) {
        CustomErrorMessage errorMessage = new CustomErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
