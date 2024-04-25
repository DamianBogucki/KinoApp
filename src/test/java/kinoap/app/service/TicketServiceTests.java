package kinoap.app.service;

import kinoap.app.Entity.Ticket;
import kinoap.app.ServicesImpl.TicketServiceImpl;
import kinoap.app.daoImpl.TicketDaoImpl;
import kinoap.app.dtoObjects.RequestDto.TicketRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTests {
    @Mock
    private TicketDaoImpl ticketDao;
    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    public void TicketService_SaveTicket_ReturnVoid() {
        TicketRequestDto ticketRequestDto = new TicketRequestDto();
        ticketRequestDto.setType("normal");
        ticketRequestDto.setPrice(10);

        doNothing().when(ticketDao).save(Mockito.any(Ticket.class));

        assertAll(() -> ticketService.saveTicket(ticketRequestDto));
    }

    @Test
    public void TicketService_DeleteTicket_ReturnVoid() {
        int ticketId = 1;
        doNothing().when(ticketDao).delete(ticketId);
        assertAll(() -> ticketService.deleteTicket(ticketId));
    }

    @Test
    public void TicketService_UpadateAllFieldsOfTicket_RetrunTicket() {
        int ticketId = 1;
        TicketRequestDto ticketRequestDto = new TicketRequestDto();
        ticketRequestDto.setType("normal");
        ticketRequestDto.setPrice(10);

        Ticket ticket = new Ticket("normal",10);

        when(ticketDao.findTicketById(ticketId)).thenReturn(ticket);
        doNothing().when(ticketDao).update(Mockito.any(Ticket.class));

        Ticket ticketUpdated = ticketService.updateTicket(ticketId, ticketRequestDto);

        assertThat(ticketUpdated).isNotNull();
        assertThat(ticketUpdated.getType()).isEqualTo(ticketRequestDto.getType());
    }

    @Test
    public void TicketService_UpadateSomeFieldsOfTicket_ReturnTicket() {
        int ticketId = 1;
        HashMap<String, Object> newData = new HashMap<>();
        newData.put("price",15);


        Ticket ticket = new Ticket("normal",10);
        when(ticketDao.findTicketById(ticketId)).thenReturn(ticket);
        Ticket ticketUpdated = ticketService.updateTicket(ticketId, newData);
        assertThat(ticketUpdated).isNotNull();
        assertThat(ticketUpdated.getType()).isEqualTo(newData.get("price"));
    }

    @Test
    public void TicketService_FindAllTickets_ReturnTickets() {
        List<Ticket> tickets = Mockito.mock(List.class);
        when(ticketDao.findAllTickets()).thenReturn(tickets);

        List<Ticket> ticketsGet = ticketService.findAllTickets();
        assertThat(ticketsGet).isNotNull();
    }

    @Test
    public void TicketService_FindTicketById_ReturnOptTicket() {
        int ticketId = 1;

        when(ticketDao.findTicketById(ticketId)).thenReturn(new Ticket());
        Ticket ticketOpt = ticketService.findTicketById(ticketId).get();
        assertThat(ticketOpt).isNotNull();
    }

    @Test
    public void TicketService_FindTicketByType_ReturnTicket() {
        String ticketType = "normal";

        when(ticketDao.findTicketByType(ticketType)).thenReturn(Optional.of(new Ticket()));
        Ticket ticketOpt = ticketService.findTicketByType(ticketType).get();
        assertThat(ticketOpt).isNotNull();
    }
}
