package com.training.controllers;

import com.training.models.Passenger;
import com.training.services.impl.PassengerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PassengerController {

    private PassengerServiceImpl passengerService;

    public PassengerController(PassengerServiceImpl passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping("/passengers")
    public ResponseEntity<List<Passenger>> getPassengersByUser(
            @RequestHeader("username") String username,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "50") Integer pageSize
    ){
        List<Passenger> passengers = passengerService.getPassengersByUser(username, pageNumber, pageSize);

        return new ResponseEntity<>(passengers,HttpStatus.OK);
    }

    @GetMapping("/passengers/{passengerId}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable String passengerId){
        Passenger passenger = passengerService.getPassengerById(passengerId);

        return new ResponseEntity<>(passenger,HttpStatus.OK);
    }

    @PostMapping("/passengers")
    public ResponseEntity<String> createPassenger(@RequestBody Passenger passenger, @RequestHeader("username") String username){
        String message = passengerService.createPassenger(passenger,username);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/passengers/{passengerId}")
    public ResponseEntity<String> updatePassenger(@RequestBody Passenger passenger,@PathVariable String passengerId, @RequestHeader("username") String username){
        String message = passengerService.updatePassenger(passenger,passengerId,username);

        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @DeleteMapping("/passengers/{passengerId}")
    public ResponseEntity<String> deletePassenger(@PathVariable String passengerId){
        String message = passengerService.deletePassenger(passengerId);

        return new ResponseEntity<>(message,HttpStatus.OK);
    }
}
