package com.training.services.impl;

import com.training.config.AppConstants;
import com.training.exception.APIException;
import com.training.models.Booking;
import com.training.models.Passenger;
import com.training.models.Ticket;
import com.training.models.User;
import com.training.payload.TicketBookingDTO;
import com.training.payload.TicketDTO;
import com.training.repositories.BookingRepository;
import com.training.repositories.PassengerRepository;
import com.training.repositories.TicketRepository;
import com.training.repositories.UserRepository;
import com.training.services.TicketService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final BookingServiceImpl bookingService;

    public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository, BookingRepository bookingRepository, PassengerRepository passengerRepository, BookingServiceImpl bookingService) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
        this.bookingService = bookingService;
    }

    @Override
    public List<TicketDTO> getTicketsByUser(String username, Integer pageNumber, Integer pageSize) {
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize);

        List<Ticket> tickets = ticketRepository.findAllByUser_UserName(username, pageDetails).getContent();
        if (tickets.isEmpty())
            throw new APIException("No tickets found");

        List<TicketDTO> ticketDTOS = tickets.stream().map(this::ticketToDto).toList();

        return ticketDTOS;
    }

    @Override
    public List<TicketDTO> getAllTickets(Integer pageNumber, Integer pageSize) {
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize);
        List<Ticket> tickets = ticketRepository.findAll(pageDetails).getContent();
        if (tickets.isEmpty())
            throw new APIException("No tickets found");

        List<TicketDTO> ticketDTOS = tickets.stream().map(this::ticketToDto).toList();

        return ticketDTOS;
    }

    @Override
    public String createTickets(String username, String bookingId, List<String> passengerIds, List<Integer> selectedSeats) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(()-> new APIException(AppConstants.USER_NOT_FOUND));

        Booking booking = bookingRepository.findByBookingId(bookingId);

        if (booking == null)
            throw new APIException(AppConstants.BOOKING_NOT_FOUND);

        if (booking.getAvailableSeats() == 0)
            throw new APIException(AppConstants.BOOKING_CLOSED);

        if (passengerIds.isEmpty())
            throw new APIException("Please select at-least one passenger");

        if (passengerIds.size()>booking.getAvailableSeats())
            throw new APIException("Not enough tickets: Number of selected passengers-"+passengerIds.size()+", Available Seats-"+booking.getAvailableSeats());

        Map<Integer, Boolean> seats = booking.getSeats();

        selectedSeats.forEach(seat-> {
            if (!seats.containsKey(seat))
                throw new APIException("Seat number Invalid");

            if (!seats.get(seat))
                throw new APIException("Seat not available");
        });

        selectedSeats.forEach(seat-> seats.put(seat, false));
        booking.setSeats(seats);

        int i = 0;
        for (String passengerId : passengerIds){
            Passenger passenger = passengerRepository.findByPassengerId(passengerId);

            if (passenger == null)
                throw new APIException(AppConstants.PASSENGER_NOT_FOUND);

            Ticket ticket = new Ticket();
            ticket.setBooking(booking);
            ticket.setUser(user);
            ticket.setPassenger(passenger);
            booking.setAvailableSeats(booking.getAvailableSeats()-1);

            ticket.setSeatNumber(selectedSeats.get(i));
            ticketRepository.save(ticket);

            i++;
        }

        if (booking.getAvailableSeats() == 0)
            booking.setBookingStatus(AppConstants.BOOKING_CLOSED);
        else if(booking.getAvailableSeats() <= 20)
            booking.setBookingStatus(AppConstants.BOOKING_FILLING_FAST);

        bookingRepository.save(booking);

        return "Tickets Generated";
    }

    @Override
    public String deleteTicket(String ticketId) {
        if (!ticketRepository.existsByTicketId(ticketId))
            throw new APIException(AppConstants.TICKET_NOT_FOUND);

        ticketRepository.deleteByTicketId(ticketId);

        return "Ticket Deleted";
    }

    public TicketDTO ticketToDto(Ticket ticket){
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketId(ticket.getTicketId());

        Booking booking = ticket.getBooking();
        TicketBookingDTO ticketBookingDTO = bookingService.ticketBookingToDto(booking);
        ticketDTO.setTicketBookingDTO(ticketBookingDTO);
        ticketDTO.setPassenger(ticket.getPassenger());
        ticketDTO.setBookingTime(ticket.getBookingTime());
        ticketDTO.setSeatNumber(ticket.getSeatNumber());

        return ticketDTO;
    }
}
