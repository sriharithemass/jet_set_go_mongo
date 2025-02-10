package com.training.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ticket {
    @Id
    private String ticketId;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime bookingTime;
    private Integer seatNumber;

    @DBRef
    private Booking booking;

    @DBRef
    @JsonIgnore
    private User user;

    @DBRef
    private Passenger passenger;
}