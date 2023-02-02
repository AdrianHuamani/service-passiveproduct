package com.bootcamp.passiveProduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.passiveProduct.domain.Commision;

import reactor.core.publisher.Mono;

@Repository
public interface CommisionRepository extends ReactiveMongoRepository<Commision, String> {

	Mono<Commision> findByTypeAndAccountType(String type, String accountType);
}
