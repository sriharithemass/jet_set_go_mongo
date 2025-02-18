package com.training.services.impl;

import com.training.config.AppConstants;
import com.training.exception.APIException;
import com.training.models.Flight;
import com.training.models.Operator;
import com.training.payload.FlightGrouped;
import com.training.payload.FlightOperatorLocation;
import com.training.repositories.FlightRepository;
import com.training.repositories.OperatorRepository;
import com.training.services.FlightService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightRepository flightRepository;
    private OperatorRepository operatorRepository;

    public FlightServiceImpl(FlightRepository flightRepository, OperatorRepository operatorRepository) {
        this.flightRepository = flightRepository;
        this.operatorRepository = operatorRepository;
    }

    @Override
    public List<Flight> getAllFlights(Integer pageNumber, Integer pageSize) {
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize);

        List<Flight> flights = flightRepository.findAll(pageDetails).getContent();
        if (flights.isEmpty())
            throw new APIException("Flight list is empty");

        return flights;
    }

    @Override
    public Flight getFlightById(String flightId) {
        Flight flight = flightRepository.findByFlightId(flightId);

        if (flight == null)
            throw new APIException(AppConstants.FLIGHT_NOT_FOUND);

        return flight;
    }

    @Override
    public String createFlight(Flight flight) {
        if (flightRepository.existsByFlightName(flight.getFlightName()))
            throw new APIException("flight name already exists");

        if (flightRepository.existsByFlightNumber(flight.getFlightNumber()))
            throw new APIException("flight number already exists");

        if (flight.getDepartureLocation().equalsIgnoreCase(flight.getArrivalLocation()))
            throw new APIException("location should not be same");

        Operator operator = operatorRepository.findById(flight.getOperator().getOperatorId())
                .orElseThrow(()-> new APIException("Operator Not Found"));
        flight.setOperator(operator);
        flightRepository.save(flight);
        return "Flight Added";
    }

    @Override
    public String updateFlight(Flight flight, String flightId) {
        if (!flightRepository.existsByFlightId((flightId)))
            throw new APIException(AppConstants.FLIGHT_NOT_FOUND);

        if (flightRepository.existsByFlightName(flight.getFlightName())
                && !flightRepository.findByFlightId(flightId).getFlightName().equalsIgnoreCase(flight.getFlightName())){
            throw new APIException("flight name already exists");
        }

        if (flightRepository.existsByFlightNumber(flight.getFlightNumber())
                && !flightRepository.findByFlightId(flightId).getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())){
            throw new APIException("flight number already exists");
        }

        flight.setFlightId(flightId);
        flightRepository.save(flight);

        return "Flight Updated";
    }

    @Override
    public String deleteFlight(String flightId) {
        if (!flightRepository.existsByFlightId(flightId))
            throw new APIException(AppConstants.FLIGHT_NOT_FOUND);

        flightRepository.deleteByFlightId(flightId);

        return "Flight Deleted";
    }

    public List<Flight> getFlightByOperatorName(String operatorName){
        List<Operator> operators = operatorRepository.findByOperatorName(operatorName);

        if (operators==null)
            throw new APIException("Operator Not Found");

        List<Flight> flights = flightRepository.findByOperator_OperatorName(operatorName);
        return flights;
    }

    public List<Flight> getFlightByOperatorLocation(String location){
        List<Operator> operators = operatorRepository.findByOperatorLocation(location);

        if (operators==null)
            throw new APIException("No Operators Found");

        List<Flight> flights = flightRepository.findByOperatorLocations(location);

        if (flights==null)
            throw new APIException(AppConstants.FLIGHT_NOT_FOUND);

        return flights;
    }

    public List<FlightGrouped> groupByOperator(){
        List<FlightGrouped> flightGroupedList = flightRepository.groupByOperator();
        return flightGroupedList;
    }

    public List<FlightOperatorLocation> groupByOperatorLocation(){
        List<FlightOperatorLocation> flightOperatorLocationList = flightRepository.groupByOperatorLocation();
        return flightOperatorLocationList;
    }
}
