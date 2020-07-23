package com.bootcamp.customer.services;

import com.bootcamp.customer.model.Pyme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PymeService {
    @Autowired
    private PymeService pymeService;

    public Flux<Pyme> findAll() {
        return pymeService.findAll();
    }

    public Mono<Pyme> save(Pyme newPyme) {
        return pymeService.save(newPyme);
    }

    public Mono<Pyme> findById(String pymeId) {
        return pymeService.findById(pymeId);
    }

    public Mono<Void> delete(Pyme vip) {
        return pymeService.delete(vip);
    }
}