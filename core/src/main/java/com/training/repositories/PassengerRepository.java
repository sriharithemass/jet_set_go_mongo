package com.training.repositories;

import com.training.models.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends MongoRepository<Passenger,Long> {
    Page<Passenger> findAllByUser_UserName(String username, Pageable pageable);

    <T> Passenger findByPassengerId(String passengerId);

    boolean existsByPassengerId(String passengerId);

    void deleteByPassengerId(String passengerId);
}
