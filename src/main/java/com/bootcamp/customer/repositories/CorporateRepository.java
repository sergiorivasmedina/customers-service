package com.bootcamp.customer.repositories;

import com.bootcamp.customer.model.Corporate;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateRepository extends ReactiveMongoRepository<Corporate, String> {
    
}