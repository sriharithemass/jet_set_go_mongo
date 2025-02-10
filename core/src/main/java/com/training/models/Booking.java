package com.training.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking {
    @Id
    private String bookingId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalDate;

    private Map<Integer, Boolean> seats = new HashMap<>();
    private Integer availableSeats;

    private String bookingStatus;

    @DBRef
    private Flight flight;

    @DBRef
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();
}