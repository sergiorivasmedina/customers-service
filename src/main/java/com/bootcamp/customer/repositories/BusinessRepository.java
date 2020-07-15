package com.bootcamp.customer.repositories;

import com.bootcamp.customer.model.Business;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends ReactiveMongoRepository<Business, String> {
    
}