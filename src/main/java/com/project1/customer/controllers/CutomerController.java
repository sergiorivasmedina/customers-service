package com.project1.customer.controllers;

import java.util.List;

import com.project1.customer.model.Business;
import com.project1.customer.model.Customer;
import com.project1.customer.model.Personal;
import com.project1.customer.repositories.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CutomerController {
    
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(value = "/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PutMapping(value = "/newPersonalAccount")
    public @ResponseBody Customer insertPersonalAccount(@RequestBody Personal personalAccount) {
        return customerRepository.save(personalAccount);
    }

    @PutMapping(value = "/newBusinessAccount")
    public @ResponseBody Customer insertBusinessAccount(@RequestBody Business businessAccount) {
        return customerRepository.save(businessAccount);
    }
}