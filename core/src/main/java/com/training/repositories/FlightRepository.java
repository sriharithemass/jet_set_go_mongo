package com.training.repositories;

import com.training.models.Flight;
import com.training.payload.FlightPrice;
import com.training.payload.FlightGrouped;
import com.training.payload.FlightOperatorLocation;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends MongoRepository<Flight, Long> {
    boolean existsByFlightName(String flightName);
    boolean existsByFlightNumber(String flightNumber);

    Flight findByFlightId(String flightId);

    boolean existsByFlightId(String flightId);

    void deleteByFlightId(String flightId);

    @Query("{'operator.operatorName' : ?0}")
    List<Flight> findByOperator_OperatorName(String operatorName);

    @Query(value = "{'operator.operatorLocations': ?0}")
    List<Flight> findByOperatorLocations(String location);

    @Aggregation(pipeline = {"{$group: {_id: '$operator.operatorName', count: {$sum: 1}}}"})
    List<FlightGrouped> groupByOperator();

    @Aggregation(pipeline = {"{$unwind: {path: '$operator.operatorLocations', preserveNullAndEmptyArrays: true}}"})
    List<FlightOperatorLocation> groupByOperatorLocation();

    @Aggregation(pipeline = {"{$group: {_id: '$operator.operatorName', maxPrice: {$max: '$flightPrice'}}}"})
    List<FlightPrice> maxFlightPriceByOperator();

    @Aggregation(pipeline = {"{$group: {_id: '$operator.operatorName', minPrice: {$min: '$flightPrice'}}}"})
    List<FlightPrice> minFlightPriceByOperator();

    @Aggregation(pipeline = {"{$sort: {flightPrice: -1}}", "{$limit: 5}"})
    List<Flight> findTop5ExpensiveFlights();

    @Aggregation(pipeline = {"{$project: {flightName: 1, flightNumber: 1, flightPrice: 1}}"})
    List<Flight> projectFlightNameNumberPrice();

    @Aggregation(pipeline = {"{$skip: 10}", "{$limit: 5}"})
    List<Flight> skipFirst10AndLimit5Flights();
}
