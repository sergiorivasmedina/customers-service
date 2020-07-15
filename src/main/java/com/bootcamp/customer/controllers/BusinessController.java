package com.bootcamp.customer.controllers;

import com.bootcamp.customer.model.Business;
import com.bootcamp.customer.repositories.BusinessRepository;

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
public class BusinessController {
    
    @Autowired
    private BusinessRepository businessRepository;

    @GetMapping(value = "/business")
    public @ResponseBody Flux<Business> getAllBusiness() {
        // list all data in business collection
        return businessRepository.findAll();
    }

    @PostMapping(value = "/business/new")
    public Mono<Business> newBusiness(@RequestBody Business newBusiness) {
        // adding a new business to the collection
        return businessRepository.save(newBusiness);
    }

    @PutMapping(value = "/business/{businessId}")
    public Mono<ResponseEntity<Business>> updateBusiness(@PathVariable(name = "businessId") String businessId, @RequestBody Business business) {
        return businessRepository.findById(businessId)
            .flatMap(existingBusiness -> {
                return businessRepository.save(business);
            })
            .map(updateBusiness -> new ResponseEntity<>(updateBusiness, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/business/{businessId}")
    public Mono<ResponseEntity<Void>> deleteBusiness(@PathVariable(name = "businessId") String businesId) {
        return businessRepository.findById(businesId)
            .flatMap(existingBusiness ->
                businessRepository.delete(existingBusiness)
                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))) 
            )
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}