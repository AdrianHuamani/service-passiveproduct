package com.bootcamp.passiveProduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.passiveProduct.domain.PersonalAccount;

import reactor.core.publisher.Mono;

@Repository
public interface PersonalAccountRepository extends ReactiveMongoRepository<PersonalAccount, String> {

	Mono<PersonalAccount> findByAccountNumber(String accountNumber);
	
	
}
