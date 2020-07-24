package com.bootcamp.customer.repositories;

import com.bootcamp.customer.model.Customer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    
    Flux<Customer> findByIdentityNumber(String id);
}