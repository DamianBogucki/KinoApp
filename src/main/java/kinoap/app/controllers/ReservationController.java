package kinoap.app.controllers;

import jakarta.validation.Valid;
import kinoap.app.Entity.Reservation;
import kinoap.app.Exceptions.ReservationNotExistsException;
import kinoap.app.dtoObjects.Mapper;
import kinoap.app.dtoObjects.RequestDto.ReservationRequstDto;
import kinoap.app.dtoObjects.ResponseDto.ReservationResponseDto;
import kinoap.app.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReservation(@RequestBody @Valid ReservationRequstDto reservationRequstDto){
        reservationService.saveReservation(reservationRequstDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations(){
        List<ReservationResponseDto> reservations = reservationService.findAllClientReservations().stream()
                .map(Mapper::reservationToResponseDto).toList();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/getReservation/{resId}")
    public ResponseEntity<ReservationResponseDto> getReservation(@PathVariable long resId){
        Optional<Reservation> reservationOpt = reservationService.findReservationById(resId);
        if (reservationOpt.isEmpty()){
            throw new ReservationNotExistsException("There is no reservation with id of " + resId);
        }
        ReservationResponseDto reservationResponseDto = Mapper.reservationToResponseDto(reservationService.findReservationById(resId).get());
        return new ResponseEntity<>(reservationResponseDto, HttpStatus.OK);
    }

    @PutMapping("/edit/{resId}")
    public ResponseEntity<ReservationResponseDto> updateReservation(@PathVariable long resId, @RequestBody @Valid ReservationRequstDto reservationRequstDto){
        Optional<Reservation> reservationOpt = reservationService.findReservationById(resId);
        if (reservationOpt.isEmpty()){
            throw new ReservationNotExistsException("There is no reservation with id of " + resId);
        }
        ReservationResponseDto reservationResponseDto = Mapper.reservationToResponseDto(reservationService.updateReservation(resId, reservationRequstDto));
        return new ResponseEntity<>(reservationResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{resId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservation(@PathVariable long resId){
        Optional<Reservation> reservationOpt = reservationService.findReservationById(resId);
        if (reservationOpt.isEmpty()){
            throw new ReservationNotExistsException("There is no reservation with id of " + resId);
        }
        reservationService.deleteReservation(resId);
    }
}
