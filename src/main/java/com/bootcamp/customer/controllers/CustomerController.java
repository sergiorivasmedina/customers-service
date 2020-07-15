package com.bootcamp.customer.controllers;

import com.bootcamp.customer.dto.CustomerDTO;
import com.bootcamp.customer.model.Business;
import com.bootcamp.customer.model.Customer;
import com.bootcamp.customer.model.Personal;
import com.bootcamp.customer.repositories.BusinessRepository;
import com.bootcamp.customer.repositories.CustomerRepository;
import com.bootcamp.customer.repositories.PersonalRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private BusinessRepository BusinessRepository;

    @GetMapping(value = "/customers")
    public @ResponseBody Flux<Customer> getAllCustomers() {
        // list all data in customer collection
        return customerRepository.findAll();
    }

    @PostMapping(value = "/customer/new")
    public void newCustomer(@RequestBody CustomerDTO newCustomerDTO) {

        if (newCustomerDTO.getType() == 1) {
            // type=1 is Personal Customer
            Personal p = new Personal(newCustomerDTO.getIdentityNumber());
            personalRepository.save(p)
                    .flatMap(per -> {
                        // adding a new customer to the collection
                        Customer c = new Customer(newCustomerDTO.getName(), newCustomerDTO.getType(),per.getIdPersonal());
                        return customerRepository.save(c);
                    })
                    .subscribe();
        }
        else if(newCustomerDTO.getType() == 2) {
            //type=2 is Business Customer
            Business b = new Business();
            b.setRuc(newCustomerDTO.getIdentityNumber());
            BusinessRepository.save(b)
                    .flatMap(bus -> {
                        // adding a new customer to the collection
                        Customer c = new Customer(newCustomerDTO.getName(), newCustomerDTO.getType(),bus.getIdBusiness());
                        return customerRepository.save(c);
                    })
                    .subscribe();
        }
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