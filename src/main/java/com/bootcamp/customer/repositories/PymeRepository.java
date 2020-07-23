package com.bootcamp.customer.repositories;

import com.bootcamp.customer.model.Pyme;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PymeRepository extends ReactiveMongoRepository<Pyme, String> {
    
}