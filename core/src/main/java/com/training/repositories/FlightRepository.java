package com.training.repositories;

import com.training.models.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends MongoRepository<Flight, Long> {
    boolean existsByFlightName(String flightName);
    boolean existsByFlightNumber(String flightNumber);

    Flight findByFlightId(String flightId);

    boolean existsByFlightId(String flightId);

    void deleteByFlightId(String flightId);
}
