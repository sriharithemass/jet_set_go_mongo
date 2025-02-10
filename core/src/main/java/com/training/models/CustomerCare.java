package com.training.models;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "customer_care_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerCare {
    @Id
    private String messageId;

    @NotBlank
    private String subject;
    @NotBlank
    private String message;
    @CreatedDate
    private LocalDate timeStamp;

    @DBRef
    private User user;
}