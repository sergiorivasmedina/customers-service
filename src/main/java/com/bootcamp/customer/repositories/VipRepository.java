package com.bootcamp.customer.repositories;

import com.bootcamp.customer.model.Vip;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VipRepository extends ReactiveMongoRepository<Vip, String> {
    
}