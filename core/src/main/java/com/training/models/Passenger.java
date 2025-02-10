package com.training.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "passenger")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Passenger {
    @Id
    private String passengerId;

    @NotBlank
    private String passengerName;
    @NotNull
    @Min(value = 18)
    private Integer age;
    @NotBlank
    private String gender;

    @DBRef
    @JsonIgnore
    private User user;

    @DBRef
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

    public Passenger(String passengerId, String passengerName, Integer age, String gender) {
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.age = age;
        this.gender = gender;
    }
}