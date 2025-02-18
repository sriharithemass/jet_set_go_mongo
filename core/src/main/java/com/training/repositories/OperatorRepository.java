package com.training.repositories;

import com.training.models.Operator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorRepository extends MongoRepository<Operator, String> {
    List<Operator> findByOperatorName(String operatorName);

    @Query("{'operatorLocation': ?0}")
    List<Operator> findByOperatorLocation(String location);


}
