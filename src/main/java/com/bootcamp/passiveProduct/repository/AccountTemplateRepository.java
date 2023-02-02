package com.bootcamp.passiveProduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.passiveProduct.domain.AccountTemplate;

import reactor.core.publisher.Mono;

@Repository
public interface AccountTemplateRepository extends ReactiveMongoRepository<AccountTemplate, String> {

	Mono<AccountTemplate> findByAccountTypeAndCurrency(String accountType,String currency);
}
