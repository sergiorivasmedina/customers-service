package com.project1.customer.repositories;

import java.util.List;

import com.project1.customer.model.Customer;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, Integer> {
    
    List<Customer> findAll();
}