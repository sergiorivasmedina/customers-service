package com.bootcamp.customer.services;

import com.bootcamp.customer.model.Customer;
import com.bootcamp.customer.repositories.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @CircuitBreaker(name = "service1", fallbackMethod = "fallbackForFindAll")
    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    //fallback
    public Flux<Customer> fallbackForFindAll(Throwable t) {
        logger.error("Inside Circuit Breaker fallbackForFindAll, cause {}", t.toString());
        return Flux.empty();
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