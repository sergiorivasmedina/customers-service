package com.bootcamp.customer.repositories;

import com.bootcamp.customer.model.Customer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    
    
}