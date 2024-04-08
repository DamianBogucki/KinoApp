package kinoap.app.controllers;

import jakarta.validation.Valid;
import kinoap.app.Entity.Ticket;
import kinoap.app.Exceptions.TicketAlreadyExistsException;
import kinoap.app.Exceptions.TicketNotFoundException;
import kinoap.app.dtoObjects.Mapper;
import kinoap.app.dtoObjects.RequestDto.TicketRequestDto;
import kinoap.app.dtoObjects.ResponseDto.TicketResponseDto;
import kinoap.app.services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TicketResponseDto>> getAllTickets(){
        List<TicketResponseDto> tickets = ticketService.findAllTickets().stream().map((Mapper::ticketToResponseDto)).toList();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
    @GetMapping("/getTicket/{ticketId}")
    public ResponseEntity<TicketResponseDto> getTicket(@PathVariable long ticketId){
        Optional<Ticket> ticket = ticketService.findTicketById(ticketId);
        if (ticket.isEmpty()){
            throw new TicketNotFoundException("There is no ticket with id of " + ticketId);
        }
        TicketResponseDto ticketResponseDto = Mapper.ticketToResponseDto(ticket.get());
        return new ResponseEntity<>(ticketResponseDto,HttpStatus.OK);
    }
    @PostMapping("/addTicket")
    public ResponseEntity<?> addNewTicket(@RequestBody @Valid TicketRequestDto ticketRequestDto){
        Optional<Ticket> ticket = ticketService.findTicketByType(ticketRequestDto.getType());
        if (ticket.isPresent()){
            throw new TicketAlreadyExistsException("There is tichet of that type " + ticketRequestDto.getType());
        }
        ticketService.saveTicket(ticketRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/editTicket/{ticketId}")
    public ResponseEntity<TicketResponseDto> editTicket(@PathVariable long ticketId, @Valid @RequestBody TicketRequestDto ticketRequestDto){
        Optional<Ticket> ticket = ticketService.findTicketById(ticketId);
        if (ticket.isEmpty()){
            throw new TicketNotFoundException("There is no ticket with this id " + ticketId);
        }
        TicketResponseDto ticketResponseDto = Mapper.ticketToResponseDto(ticketService.updateTicket(ticketId,ticketRequestDto));
        return new ResponseEntity<>(ticketResponseDto,HttpStatus.OK);
    }

    @PatchMapping("/editTicket/{ticketId}")
    public ResponseEntity<TicketResponseDto> editTicketByVariables(@PathVariable long ticketId,@RequestBody Map<String,Object> variablesToUpdate){
        Optional<Ticket> ticketOpt = ticketService.findTicketById(ticketId);
        if (ticketOpt.isEmpty()){
            throw new TicketNotFoundException("There is no ticket with this id " + ticketId);
        }
        TicketResponseDto ticketResponseDto = Mapper.ticketToResponseDto(ticketService.updateTicket(ticketId,variablesToUpdate));
        return new ResponseEntity<>(ticketResponseDto,HttpStatus.OK);
    }


    @DeleteMapping("/deleteTicket/{ticketId}")
    public ResponseEntity<?> deleteTicket(@PathVariable long ticketId){
        Optional<Ticket> ticketOpt = ticketService.findTicketById(ticketId);
        if (ticketOpt.isEmpty()){
            throw new TicketNotFoundException("There is no ticket with id of " + ticketId);
        }
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
