package kinoap.app.controllers;

import jakarta.validation.Valid;
import kinoap.app.Entity.Snack;
import kinoap.app.Entity.Ticket;
import kinoap.app.ExceptionResponses.SnackErrorResponse;
import kinoap.app.Exceptions.SnackAlreadyExistsException;
import kinoap.app.Exceptions.SnackNotFoundException;
import kinoap.app.Exceptions.TicketAlreadyExistsException;
import kinoap.app.Exceptions.TicketNotFoundException;
import kinoap.app.dtoObjects.Mapper;
import kinoap.app.dtoObjects.RequestDto.SnackRequestDto;
import kinoap.app.dtoObjects.RequestDto.TicketRequestDto;
import kinoap.app.dtoObjects.ResponseDto.SnackResponseDto;
import kinoap.app.dtoObjects.ResponseDto.TicketResponseDto;
import kinoap.app.services.SnackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/snacks")
public class SnackController {
    private final SnackService snackService;

    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SnackResponseDto>> getAllSnacks(){
        List<SnackResponseDto> snacks = snackService.findAllSnacks().stream().map((Mapper::snackToResponseDto)).toList();
        return new ResponseEntity<>(snacks, HttpStatus.OK);
    }

    @GetMapping("/getSnack/{snackId}")
    public ResponseEntity<SnackResponseDto> getSnack(@PathVariable long snackId){
        Optional<Snack> snack = snackService.findSnackById(snackId);
        if (snack.isEmpty()){
            throw new SnackNotFoundException("There is no snack with id of " + snackId);
        }
        SnackResponseDto snackResponseDto = Mapper.snackToResponseDto(snack.get());
        return new ResponseEntity<>(snackResponseDto,HttpStatus.OK);
    }

    @PostMapping("/addSnack")
    public ResponseEntity<?> addNewSnack(@RequestBody @Valid SnackRequestDto snackRequestDto){
        Optional<Snack> snack = snackService.findSnackByName(snackRequestDto.getName());
        if (snack.isPresent()){
            throw new SnackAlreadyExistsException("There is snack of that name " + snackRequestDto.getName());
        }
        snackService.saveSnack(snackRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/editSnack/{snackId}")
    public ResponseEntity<SnackResponseDto> editSnack(@PathVariable long snackId, @RequestBody @Valid SnackRequestDto snackRequestDto){
        Optional<Snack> ticketOpt = snackService.findSnackById(snackId);
        if (ticketOpt.isEmpty()){
            throw new SnackNotFoundException("There is no snack with this id " + snackId);
        }
        SnackResponseDto snackResponseDto = Mapper.snackToResponseDto(snackService.updateSnack(snackId,snackRequestDto));
        return new ResponseEntity<>(snackResponseDto,HttpStatus.OK);
    }

    @PatchMapping("/editSnack/{snackId}")
    public ResponseEntity<SnackResponseDto> editSnackByVariables(@PathVariable long snackId,@RequestBody Map<String,Object> variablesToUpdate){
        Optional<Snack> snackOpt = snackService.findSnackById(snackId);
        if (snackOpt.isEmpty()){
            throw new SnackNotFoundException("There is no snack with this id " + snackId);
        }
        SnackResponseDto snackResponseDto = Mapper.snackToResponseDto(snackService.updateSnack(snackId,variablesToUpdate));
        return new ResponseEntity<>(snackResponseDto,HttpStatus.OK);
    }

    @DeleteMapping("/deleteSnack/{snackId}")
    public ResponseEntity<?> deleteSnack(@PathVariable long snackId){
        Optional<Snack> snackOpt = snackService.findSnackById(snackId);
        if (snackOpt.isEmpty()){
            throw new SnackNotFoundException("There is no snack with id of " + snackId);
        }
        snackService.deleteSnack(snackId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
