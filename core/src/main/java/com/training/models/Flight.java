package com.training.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "flight")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Flight {
    @Id
    private String flightId;

    @NotBlank
    private String flightName;
    @NotBlank
    private String flightNumber;
    @NotBlank
    private String flightType;
    @NotBlank
    private String departureLocation;
    @NotBlank
    private String arrivalLocation;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime arrivalTime;

    @NotNull
    private Integer totalSeats;
    @NotNull
    private Double flightPrice;

    @DBRef
    @JsonIgnore
    private List<Booking> bookings = new ArrayList<>();

    private Operator operator;
}