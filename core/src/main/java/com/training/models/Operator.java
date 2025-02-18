package com.training.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "operator")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Operator {
    @Id
    private String operatorId;

    @NotBlank
    private String operatorName;

    @NotBlank
    private List<String> operatorLocations;
}
