package com.training.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private String userId;

    @NotBlank
    @Size(max = 20)
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 120)
    @JsonIgnore
    private String password;

    @DBRef
    @JsonBackReference
    @ToString.Exclude
    private Role role;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @DBRef
    private List<Passenger> passengers = new ArrayList<>();

    @DBRef
    private List<Ticket> tickets = new ArrayList<>();

    @DBRef
    private List<CustomerCare> customerCareMessages = new ArrayList<>();

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}