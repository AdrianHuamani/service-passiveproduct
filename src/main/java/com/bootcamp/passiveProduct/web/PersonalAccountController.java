package com.bootcamp.passiveProduct.web;


import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.passiveProduct.domain.PersonalAccount;
import com.bootcamp.passiveProduct.service.PersonalAccountService;

import com.bootcamp.passiveProduct.web.mapper.PersonalAccountMapper;
import com.bootcamp.passiveProduct.web.mapper.PersonalAccountTransactionMapper;
import com.bootcamp.passiveProduct.web.model.PersonalAccountModel;
import com.bootcamp.passiveProduct.web.model.PersonalAccountTransactionModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/personalAccount")
public class PersonalAccountController {
	
	@Value("${spring.application.name}")
	String name;
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private PersonalAccountService personalAccountService;
	
	@Autowired
	private PersonalAccountMapper personalAccountMapper;
	
	@Autowired
	private PersonalAccountTransactionMapper personalAccountTransactionMapper;
	
	@GetMapping()
	public Mono<ResponseEntity<Flux<PersonalAccountModel>>> getAll(){
		log.info("Get All executed");
		return Mono.just(ResponseEntity.ok()
				.body(personalAccountService.findAll()
						.map(x ->personalAccountMapper.entityToModel(x))));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<PersonalAccountModel>> getById(@PathVariable String id){
		log.info("getById executed {}", id);
		Mono<PersonalAccount> response=personalAccountService.findById(id);
		return response.map(x->personalAccountMapper.entityToModel(x))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
				
	}
	
	@GetMapping("/accountNumber/{accountNumber}")
	public Mono<ResponseEntity<PersonalAccountModel>> getByAccountNumber(@PathVariable String accountNumber){
		log.info("getById executed {}", accountNumber);
		Mono<PersonalAccount> response=personalAccountService.findByAccountNumber(accountNumber);
		return response.map(x->personalAccountMapper.entityToModel(x))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
				
	}
	
	@PostMapping()
	public Mono<ResponseEntity<PersonalAccountModel>> create(@Valid @RequestBody PersonalAccountModel request){
		log.info("created executed {}", request);
		return personalAccountService.createPersonalAccount(personalAccountMapper.modelToEntity(request))
				.map(x-> personalAccountMapper.entityToModel(x))
				.flatMap(x->Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "PersonalAccount", x.getId())))
						.body(x)));
	}
	
	  @PutMapping("/{id}")
	  public Mono<ResponseEntity<PersonalAccountModel>> updateById(@PathVariable String id, @Valid @RequestBody PersonalAccountModel request){
		  log.info("updateById executed {}:{}" ,id, request);
		  return personalAccountService.update(id, personalAccountMapper.modelToEntity(request))
				  .map(x -> personalAccountMapper.entityToModel(x))
				  .flatMap(y-> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "PersonalAccount", y.getId())))
				  .body(y)))
				  .defaultIfEmpty(ResponseEntity.badRequest().build());
	  }
	
	    @DeleteMapping("/{id}")
	    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
	    	log.info("deleteById executed {}", id);
	    	return personalAccountService.delete(id)
	    			.map(x-> ResponseEntity.ok().<Void>build())
	    			.defaultIfEmpty(ResponseEntity.notFound().build());
	    }
	    
	    @PostMapping("/retirement")
		public Mono<ResponseEntity<PersonalAccountModel>> retirement(@Valid @RequestBody PersonalAccountTransactionModel request){
			log.info("Transaction executed {}", request);
			return personalAccountService.retirement(personalAccountTransactionMapper.modelToEntity(request))
					.map(x-> personalAccountMapper.entityToModel(x))
					.flatMap(x->Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "PersonalAccountTransaction", x.getId())))
							.body(x)));
		}
	    
	    @PostMapping("/deposit")
	    public Mono<ResponseEntity<PersonalAccountModel>> deposit(@Valid @RequestBody PersonalAccountTransactionModel request){
	    	log.info("Transaction executed: ", request);
	    	System.out.println(request);
	    	return personalAccountService.deposit(personalAccountTransactionMapper.modelToEntity(request))
	    			.map(x->personalAccountMapper.entityToModel(x))
	    			.flatMap(x->Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "PersonalAccountTransaction", x.getId())))
							.body(x)));
	    }
	    
	    @PostMapping("/transfer")
	    public Mono<ResponseEntity<PersonalAccountModel>> transfer(@Valid @RequestBody PersonalAccountTransactionModel request){
	    	log.info("Transaction executed: ", request);
	    	System.out.println(request);
	    	return personalAccountService.transfer(personalAccountTransactionMapper.modelToEntity(request))
	    			.map(x->personalAccountMapper.entityToModel(x))
	    			.flatMap(x->Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "PersonalAccountTransaction", x.getId())))
							.body(x)));
	    }
	    

}
