package com.bootcamp.customer.services;

import com.bootcamp.customer.model.Customer;
import com.bootcamp.customer.repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Mono<Customer> save(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    public Mono<Customer> findById(String customerId) {
        return customerRepository.findById(customerId);
    }

    public Mono<Void> delete(Customer customer) {
        return customerRepository.delete(customer);
    }
}