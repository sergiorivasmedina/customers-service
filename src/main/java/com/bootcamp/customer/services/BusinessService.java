package com.bootcamp.customer.services;

import com.bootcamp.customer.model.Business;
import com.bootcamp.customer.repositories.BusinessRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    public Flux<Business> findAll() {
        return businessRepository.findAll();
    }

    public Mono<Business> save(Business newBusiness) {
        return businessRepository.save(newBusiness);
    }

    public Mono<Business> findById(String businessId) {
        return businessRepository.findById(businessId);
    }

    public Mono<Void> delete(Business business) {
        return businessRepository.delete(business);
    }
}