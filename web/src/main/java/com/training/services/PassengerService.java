package com.training.services;

import com.training.models.Passenger;

import java.util.List;

public interface PassengerService {
    List<Passenger> getPassengersByUser(String username, Integer pageNumber, Integer pageSize);
    Passenger getPassengerById(String passengerId);
    String createPassenger(Passenger passenger, String username);
    String updatePassenger(Passenger passenger, String passengerId, String username);
    String deletePassenger(String passengerId);
}
