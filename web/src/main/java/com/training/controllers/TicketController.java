package com.training.controllers;

import com.training.payload.TicketDTO;
import com.training.payload.TicketRequest;
import com.training.services.impl.TicketServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    private final TicketServiceImpl ticketService;

    public TicketController(TicketServiceImpl ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> getTicketsByUser(
            @RequestHeader("username") String username,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "50") Integer pageSize
    ){
        List<TicketDTO> ticketDTOS = ticketService.getTicketsByUser(username, pageNumber, pageSize);

        return new ResponseEntity<>(ticketDTOS, HttpStatus.OK);
    }

    @GetMapping("/admin/all-tickets")
    public ResponseEntity<List<TicketDTO>> getAllTickets(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "50") Integer pageSize
    ){
        List<TicketDTO> ticketDTOS = ticketService.getAllTickets(pageNumber, pageSize);
        return new ResponseEntity<>(ticketDTOS, HttpStatus.OK);
    }

    @PostMapping("/tickets/{bookingId}")
    public ResponseEntity<String> createTickets(
            @RequestHeader("username") String username,
            @PathVariable String bookingId,
            @RequestBody TicketRequest ticketRequest
            ){
        List<String> passengerIds = ticketRequest.getPassengerIds();
        List<Integer> selectedSeats = ticketRequest.getSelectedSeats();
        String message = ticketService.createTickets(username,bookingId,passengerIds,selectedSeats);
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/tickets/{ticketId}")
    public ResponseEntity<String> deleteTicket(@PathVariable String ticketId){
        String message = ticketService.deleteTicket(ticketId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
}
