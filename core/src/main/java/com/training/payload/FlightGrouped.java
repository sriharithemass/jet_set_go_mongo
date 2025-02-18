package com.training.payload;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FlightGrouped {
    private String id;
    private Integer count;
}
