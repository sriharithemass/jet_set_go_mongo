package com.training.repositories;

import com.training.models.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends MongoRepository<Booking,Long> {
    @Query(value = "{ 'departureDate': ?0, 'flight.departureLocation': ?1, 'flight.arrivalLocation': ?2 }", sort = "{ 'flight.departureTime': 1 }")
    Page<Booking> getBookingsBySearch(LocalDate departureDate, String departureLocation, String arrivalLocation, Pageable pageable);

    Booking findByBookingId(String bookingId);

    boolean existsByBookingId(String bookingId);

    void deleteByBookingId(String bookingId);
}
