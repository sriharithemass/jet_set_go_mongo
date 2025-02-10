package com.training.services;

import com.training.payload.TicketDTO;

import java.util.List;

public interface TicketService {
    List<TicketDTO> getTicketsByUser(String username, Integer pageNumber, Integer pageSize);
    List<TicketDTO> getAllTickets(Integer pageNumber, Integer pageSize);
    String createTickets(String username, String bookingId, List<String> passengerIds, List<Integer> selectedSeats);
    String deleteTicket(String ticketId);
}
