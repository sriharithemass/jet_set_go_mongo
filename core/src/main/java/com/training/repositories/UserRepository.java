package com.training.repositories;

import com.training.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUserName(String username);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);

    User findByUserId(String userId);
}

