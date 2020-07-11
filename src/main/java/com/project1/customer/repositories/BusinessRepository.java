package com.project1.customer.repositories;

import com.project1.customer.model.Business;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends ReactiveMongoRepository<Business, String> {
    
}