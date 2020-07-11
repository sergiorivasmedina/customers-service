package com.project1.customer.repositories;

import com.project1.customer.model.Personal;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PersonalRepository extends ReactiveMongoRepository<Personal, String> {
    
}