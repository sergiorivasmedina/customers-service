package com.bootcamp.customer.services;

import com.bootcamp.customer.model.Vip;
import com.bootcamp.customer.repositories.VipRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VipService {
    @Autowired
    private VipRepository vipRepository;

    public Flux<Vip> findAll() {
        return vipRepository.findAll();
    }

    public Mono<Vip> save(Vip newVip) {
        return vipRepository.save(newVip);
    }

    public Mono<Vip> findById(String vipId) {
        return vipRepository.findById(vipId);
    }

    public Mono<Void> delete(Vip vip) {
        return vipRepository.delete(vip);
    }
}