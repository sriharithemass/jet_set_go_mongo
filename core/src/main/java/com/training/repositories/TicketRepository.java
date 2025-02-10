package com.training.repositories;

import com.training.models.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends MongoRepository<Ticket,Long> {
    Page<Ticket> findAllByUser_UserName(String username, Pageable pageable);

    boolean existsByTicketId(String ticketId);

    void deleteByTicketId(String ticketId);
}
