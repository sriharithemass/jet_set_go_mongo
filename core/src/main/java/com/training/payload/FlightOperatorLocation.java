package com.training.payload;

import com.training.models.Operator;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FlightOperatorLocation {
    private String flightName;
    private String flightNumber;
    private Operator operator;
}
