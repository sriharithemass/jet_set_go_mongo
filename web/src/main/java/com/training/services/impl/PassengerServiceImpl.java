package com.training.services.impl;

import com.training.config.AppConstants;
import com.training.exception.APIException;
import com.training.models.Passenger;
import com.training.models.User;
import com.training.repositories.PassengerRepository;
import com.training.repositories.UserRepository;
import com.training.services.PassengerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {

    private UserRepository userRepository;
    private PassengerRepository passengerRepository;

    public PassengerServiceImpl(UserRepository userRepository, PassengerRepository passengerRepository) {
        this.userRepository = userRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public List<Passenger> getPassengersByUser(String username, Integer pageNumber, Integer pageSize) {
        if (!userRepository.existsByUserName(username))
            throw new APIException(AppConstants.USER_NOT_FOUND);
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize);
        List<Passenger> passengers = passengerRepository.findAllByUser_UserName(username, pageDetails).getContent();

        if (passengers.isEmpty())
            throw new APIException("No passengers found");

        return passengers;
    }

    @Override
    public Passenger getPassengerById(String passengerId) {
        Passenger passenger = passengerRepository.findByPassengerId(passengerId);

        if (passenger == null)
            throw new APIException(AppConstants.PASSENGER_NOT_FOUND);

        return passenger;
    }

    @Override
    public String createPassenger(Passenger passenger, String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(()-> new APIException(AppConstants.USER_NOT_FOUND));

        passenger.setUser(user);
        passengerRepository.save(passenger);
        return "Passenger Added";
    }

    @Override
    public String updatePassenger(Passenger passenger, String passengerId, String username) {
        if (!passengerRepository.existsByPassengerId(passengerId))
            throw new APIException(AppConstants.PASSENGER_NOT_FOUND);

        User user = userRepository.findByUserName(username)
                        .orElseThrow(()-> new APIException(AppConstants.USER_NOT_FOUND));

        passenger.setPassengerId(passengerId);
        passenger.setUser(user);
        passengerRepository.save(passenger);

        return "Passenger updated";
    }

    @Override
    public String deletePassenger(String passengerId) {
        if (!passengerRepository.existsByPassengerId(passengerId))
            throw new APIException(AppConstants.PASSENGER_NOT_FOUND);

        passengerRepository.deleteByPassengerId(passengerId);

        return "Passenger Removed";
    }
}
