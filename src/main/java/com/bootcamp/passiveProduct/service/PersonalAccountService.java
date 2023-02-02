package com.bootcamp.passiveProduct.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.passiveProduct.common.ErrorMessage;
import com.bootcamp.passiveProduct.common.FunctionalException;
import com.bootcamp.passiveProduct.domain.Client;
import com.bootcamp.passiveProduct.domain.PersonalAccount;
import com.bootcamp.passiveProduct.domain.PersonalAccountTransaction;
import com.bootcamp.passiveProduct.repository.AccountTemplateRepository;
import com.bootcamp.passiveProduct.repository.CommisionRepository;
import com.bootcamp.passiveProduct.repository.PersonalAccountRepository;
import com.bootcamp.passiveProduct.web.mapper.PersonalAccountMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PersonalAccountService {

	@Autowired
	private PersonalAccountRepository personalAccountRepository;
	@Autowired
	private PersonalAccountMapper personalAccountMapper;
	
	@Autowired
	private CommisionRepository commisionRepository;
	
	@Autowired
	private AccountTemplateRepository accountTemplateRepository;
	
	
	
	
	private final WebClient webClient= WebClient.builder().baseUrl("http://localhost:8081/v1/client")
			.defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE).build();
	
	public Mono<Client> getClientByIdentityNumber(String identityNumber){
		return this.webClient.get().uri("/findByIdentityNumber/{identityNumber}", identityNumber)
				.retrieve().bodyToMono(Client.class);
	}
	

	
	public Flux<PersonalAccount> findAll(){
		log.debug("FindAll executed");
		return personalAccountRepository.findAll();
	}
	
	public Mono<PersonalAccount> findById(String id){
		log.debug("findById executed {}", id);
		return personalAccountRepository.findById(id);
	}
	
	// public Flux<Account> findByCreditCardId- findByClient findAllTransactionByAccountNumber

	
	public Mono<PersonalAccount> createPersonalAccount(PersonalAccount personalAccount){
		log.debug("Created executed {}", personalAccount);
			return getClientByIdentityNumber(personalAccount.getIdentityNumberClient())
					.flatMap(client->{
						return accountTemplateRepository.findByAccountTypeAndCurrency(personalAccount.getAccountType(), personalAccount.getCurrency())
						.flatMap(accountTemplate ->{
							personalAccount.setAvailableBalance(accountTemplate.getAvailableBalance());
							personalAccount.setBalance(accountTemplate.getBalance());
							personalAccount.setMovementFrecuency(accountTemplate.getMovementFrecuency());
							personalAccount.setMovementQuantityMax(accountTemplate.getMovementQuantityMax());
							personalAccount.setCci(generateRandomCCI(personalAccount.getAccountNumber()));
							return personalAccountRepository.save(personalAccount);
						})
						.switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.CURRENCY_OR_ACCOUNTTYPE_NOT_FOUND.getValue())));
					})
					.switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.CLIENT_NOT_FOUND.getValue())));
		
	}
	
	public Mono<PersonalAccount> update(String id, PersonalAccount personalAccount){
		log.debug("update executed {}:{}",id, personalAccount);
		return personalAccountRepository.findById(id)
				.flatMap(x -> {
					personalAccountMapper.update(x, personalAccount);
					return personalAccountRepository.save(x);
				});
	}
	
	public Mono<PersonalAccount> delete(String id){
		log.debug("delete executed {}", id);
		return personalAccountRepository.findById(id)
				.flatMap(personalAccount -> personalAccountRepository.delete(personalAccount)
				.then(Mono.just(personalAccount)));
	}
	
	private String generateRandomCCI(String accountNumber) {
		return "002"+accountNumber+"02";
	}
	
	public Mono<PersonalAccount> deposit(PersonalAccountTransaction personalAccountTransaction){
		log.debug("Deposit executed:  ",personalAccountTransaction);
			return findByAccountNumber(personalAccountTransaction.getDestinyAccount())
					.flatMap(destinyAccount->{
						if (destinyAccount.getMovementQuantityMax()==null || destinyAccount.getMovementQuantity()<=destinyAccount.getMovementQuantityMax()) {
							//destinyAccount.getTransactions().get(destinyAccount.getTransactions().size()-1);
							
							
							
							return accountTemplateRepository.findByAccountTypeAndCurrency(destinyAccount.getAccountType(), destinyAccount.getCurrency())
							.flatMap(accountTemp->{
								if (accountTemp.getMovementDate()==null || accountTemp.getMovementDate()==LocalDate.now()) {
									if (destinyAccount.getCurrency().equals(personalAccountTransaction.getCurrency())) {
										return commisionRepository.findByTypeAndAccountType("Deposito", destinyAccount.getAccountType())
												.flatMap(commision->{
													personalAccountTransaction.setCommision(commision.getValue());
													personalAccountTransaction.setTotalAmount((1-commision.getValue())*personalAccountTransaction.getAmount());
													personalAccountTransaction.setTransactionDate(LocalDate.now());
													destinyAccount.setBalance(destinyAccount.getBalance()+personalAccountTransaction.getTotalAmount());
													destinyAccount.setAvailableBalance(destinyAccount.getAvailableBalance()+personalAccountTransaction.getTotalAmount());
													destinyAccount.addTransaction(personalAccountTransaction);
													
													destinyAccount.setMovementQuantity(destinyAccount.getMovementQuantity()+1);
													return personalAccountRepository.save(destinyAccount);
												});
									}else {
										return Mono.error(new FunctionalException(ErrorMessage.CURRENCY_NOT_SUPPORTED.getValue()));
									}
								}else {
									return Mono.error(new FunctionalException(ErrorMessage.NO_TRANSACTION_THIS_DAY.getValue()));
								}
							});
						}else { //Comparar localdate.now.month buscar cantidad de movimientos de ese mes 
							return Mono.error(new FunctionalException(ErrorMessage.EXCEED_AMOUNT_OF_MOVES.getValue()));
						}
					})
					.switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.ACCOUNT_DESTINY_NOT_FOUND.name())));
	}

	public Mono<PersonalAccount> retirement(PersonalAccountTransaction personalAccountTransaction){
		log.debug("Retirement executed:  ",personalAccountTransaction);
			return findByAccountNumber(personalAccountTransaction.getSourceAccount())
					.flatMap(sourceAccount->{
						if (sourceAccount.getCurrency().equals(personalAccountTransaction.getCurrency())) {
							return commisionRepository.findByTypeAndAccountType("Retiro", sourceAccount.getAccountType())
									.flatMap(commision->{
										personalAccountTransaction.setCommision(commision.getValue());
										personalAccountTransaction.setTotalAmount((1+commision.getValue())*personalAccountTransaction.getAmount());
										personalAccountTransaction.setTransactionDate(LocalDate.now());
										if (personalAccountTransaction.getTotalAmount()<=sourceAccount.getAvailableBalance()) {
											sourceAccount.setBalance(sourceAccount.getBalance()-personalAccountTransaction.getTotalAmount());
											sourceAccount.setAvailableBalance(sourceAccount.getAvailableBalance()-personalAccountTransaction.getTotalAmount());
											sourceAccount.addTransaction(personalAccountTransaction);
											return personalAccountRepository.save(sourceAccount);
										}else {
											return Mono.error(new FunctionalException(ErrorMessage.INSUFFICIENTE_BALANCE.getValue()));
										}
									});
						}else {
							return Mono.error(new FunctionalException(ErrorMessage.CURRENCY_NOT_SUPPORTED.getValue()));
						}
					})
					.switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.ACCOUNT_ORIGIN_NOT_FOUND.getValue())));
	}
	
	
	public Mono<PersonalAccount> transfer(PersonalAccountTransaction personalAccountTransaction){
		log.debug("Transfer executed", personalAccountTransaction);
		if (personalAccountTransaction.getDestinyAccountType().equals("Mi Banco")) {
			return findByAccountNumber(personalAccountTransaction.getSourceAccount())
					.flatMap(sourceAccount->{
						return findByAccountNumber(personalAccountTransaction.getDestinyAccount())
						.flatMap(destinyAccount -> {
							if (sourceAccount.getCurrency().equals(personalAccountTransaction.getCurrency())) {
								return commisionRepository.findByTypeAndAccountType("Transferencia", sourceAccount.getAccountType())
										.flatMap(commision->{
											personalAccountTransaction.setCommision(commision.getValue());
											personalAccountTransaction.setTotalAmount((1+commision.getValue())*personalAccountTransaction.getAmount());
											personalAccountTransaction.setTransactionDate(LocalDate.now());
											if (personalAccountTransaction.getTotalAmount()<=sourceAccount.getAvailableBalance()) {
												sourceAccount.setBalance(sourceAccount.getBalance()-personalAccountTransaction.getTotalAmount());
												sourceAccount.setAvailableBalance(sourceAccount.getAvailableBalance()-personalAccountTransaction.getTotalAmount());
												destinyAccount.setBalance(destinyAccount.getBalance()+personalAccountTransaction.getTotalAmount());
												destinyAccount.setAvailableBalance(destinyAccount.getAvailableBalance()+personalAccountTransaction.getAmount());
												sourceAccount.addTransaction(personalAccountTransaction);
												destinyAccount.addTransaction(personalAccountTransaction);
												personalAccountRepository.save(sourceAccount).subscribe();
												return personalAccountRepository.save(destinyAccount);
											}else {
												return Mono.error(new FunctionalException(ErrorMessage.INSUFFICIENTE_BALANCE.getValue()));
											}
										});
							}else {
								return Mono.error(new FunctionalException(ErrorMessage.CURRENCY_NOT_SUPPORTED.getValue()));
							}
						})
						.switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.ACCOUNT_DESTINY_NOT_FOUND.getValue())));
						
					})
					.switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.ACCOUNT_ORIGIN_NOT_FOUND.getValue())));
		}else {
			return Mono.error(new FunctionalException(ErrorMessage.TRANSACTION_NOT_SUPPORTED.getValue()));
		}
		
	}
	
	
	public Mono<PersonalAccount> findByAccountNumber(String accountNumber){
		return personalAccountRepository.findByAccountNumber(accountNumber);
	}
	
}
