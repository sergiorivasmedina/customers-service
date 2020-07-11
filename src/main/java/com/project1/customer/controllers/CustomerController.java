package com.project1.customer.controllers;

import com.project1.customer.model.Customer;
import com.project1.customer.repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CustomerController {
    
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(value = "/customers")
    public @ResponseBody Flux<Customer> getAllCustomers() {
        // list all data in customer collection
        return customerRepository.findAll();
    }

    @PostMapping(value = "/customer/new")
    public Mono<Customer> newCustomer(@RequestBody Customer newCustomer) {
        // adding a new customer to the collection
        return customerRepository.save(newCustomer);
    }

    @PutMapping(value = "/customer/{customerId}")
    public Mono <ResponseEntity<Customer>> updateCustomer(@PathVariable(name = "customerId") String customerId, @RequestBody Customer customer) {
        return customerRepository.findById(customerId)
            .flatMap(existingCustomer -> {
                return customerRepository.save(customer);
            })
            .map(updateCustomer -> new ResponseEntity<>(updateCustomer, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/customer/{customerId}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable(name = "customerId") String customerId) {
        return customerRepository.findById(customerId)
            .flatMap(existingCustomer ->
                customerRepository.delete(existingCustomer)
                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))) 
            )
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}