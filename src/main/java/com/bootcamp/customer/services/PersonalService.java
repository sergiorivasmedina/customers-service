package com.bootcamp.customer.services;

import com.bootcamp.customer.model.Personal;
import com.bootcamp.customer.repositories.PersonalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonalService {
    @Autowired
    private PersonalRepository personalRepository;

    public Flux<Personal> findAll() {
        return personalRepository.findAll();
    }

    public Mono<Personal> save(Personal newPersonal) {
        return personalRepository.save(newPersonal);
    }

    public Mono<Personal> findById(String personalId) {
        return personalRepository.findById(personalId);
    }

    public Mono<Void> delete(Personal personal) {
        return personalRepository.delete(personal);
    }
}