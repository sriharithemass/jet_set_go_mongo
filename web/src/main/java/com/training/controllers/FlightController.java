package com.training.controllers;

import com.training.models.Flight;
import com.training.payload.FlightGrouped;
import com.training.payload.FlightOperatorLocation;
import com.training.services.impl.FlightServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class FlightController {

    private FlightServiceImpl flightService;

    public FlightController(FlightServiceImpl flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> getAllFlights(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "50") Integer pageSize
    ){
        List<Flight> flights = flightService.getAllFlights(pageNumber, pageSize);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/flights/{flightId}")
    public ResponseEntity<Flight> getFlightById(@PathVariable String flightId){
        Flight flight = flightService.getFlightById(flightId);
        return new ResponseEntity<>(flight,HttpStatus.OK);
    }

    @GetMapping("/flights-op")
    public ResponseEntity<List<Flight>> getFlightsByOperatorName(@RequestParam String operatorName){
        List<Flight> flights = flightService.getFlightByOperatorName(operatorName);
        return new ResponseEntity<>(flights,HttpStatus.OK);
    }

    @GetMapping("/flights-op-loc")
    public ResponseEntity<List<Flight>> getFlightsByOperatorLocation(@RequestParam String location){
        List<Flight> flights = flightService.getFlightByOperatorLocation(location);
        return new ResponseEntity<>(flights,HttpStatus.OK);
    }

    @GetMapping("/flight-group")
    public ResponseEntity<List<FlightGrouped>> getFlightGroup(){
        List<FlightGrouped> flightGroupedList = flightService.groupByOperator();
        return new ResponseEntity<>(flightGroupedList,HttpStatus.OK);
    }

    @GetMapping("/flight-group-loc")
    public ResponseEntity<List<FlightOperatorLocation>> getFlightGroupLocation(){
        List<FlightOperatorLocation> flightGroupedList = flightService.groupByOperatorLocation();
        return new ResponseEntity<>(flightGroupedList,HttpStatus.OK);
    }

    @PostMapping("/flights")
    public ResponseEntity<String> addFlight(@RequestBody Flight flight){
        String message = flightService.createFlight(flight);
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

    @PutMapping("/flights/{flightId}")
    public ResponseEntity<String> updateFlight(@RequestBody Flight flight, @PathVariable String flightId){
        String message = flightService.updateFlight(flight,flightId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @DeleteMapping("/flights/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable String flightId){
        String message = flightService.deleteFlight(flightId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
}