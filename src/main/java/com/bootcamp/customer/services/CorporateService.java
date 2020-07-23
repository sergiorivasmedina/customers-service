package com.bootcamp.customer.services;

import com.bootcamp.customer.model.Corporate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CorporateService {
    @Autowired
    private CorporateService corporateService;

    public Flux<Corporate> findAll() {
        return corporateService.findAll();
    }

    public Mono<Corporate> save(Corporate newCorporate) {
        return corporateService.save(newCorporate);
    }

    public Mono<Corporate> findById(String corporateId) {
        return corporateService.findById(corporateId);
    }

    public Mono<Void> delete(Corporate corporate) {
        return corporateService.delete(corporate);
    }
}