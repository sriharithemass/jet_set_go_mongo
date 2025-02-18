package com.training.payload;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FlightPrice {
    private String id;
    private double price;
}
