package com.training.repositories;

import com.training.models.CustomerCare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCareRepository extends MongoRepository<CustomerCare,Long> {
    Page<CustomerCare> findAllByUser_UserName(String username, Pageable pageDetails);

    boolean existsByMessageId(String messageId);

    void deleteByMessageId(String messageId);
}
