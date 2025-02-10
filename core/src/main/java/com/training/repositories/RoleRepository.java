package com.training.repositories;

import com.training.models.AppRole;
import com.training.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}