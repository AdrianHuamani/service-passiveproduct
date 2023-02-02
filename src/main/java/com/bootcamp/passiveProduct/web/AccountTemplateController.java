package com.bootcamp.passiveProduct.web;


import java.net.URI;
import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.passiveProduct.domain.AccountTemplate;
import com.bootcamp.passiveProduct.repository.AccountTemplateRepository;
import com.bootcamp.passiveProduct.web.mapper.AccountTemplateMapper;
import com.bootcamp.passiveProduct.web.model.AccountTemplateModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/accountTemplate")
public class AccountTemplateController {
	
	@Value("${spring.application.name}")
	String name;
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private AccountTemplateRepository accountTemplateRepository;
	
	@Autowired
	private AccountTemplateMapper accountTemplateMapper;
	
	@GetMapping()
	public Mono<ResponseEntity<Flux<AccountTemplateModel>>> getAll(){
		log.info("Get All executed");
		return Mono.just(ResponseEntity.ok()
				.body(accountTemplateRepository.findAll()
						.map(x ->accountTemplateMapper.entityToModel(x))));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<AccountTemplateModel>> getById(@PathVariable String id){
		log.info("getById executed {}", id);
		Mono<AccountTemplate> response=accountTemplateRepository.findById(id);
		return response.map(x->accountTemplateMapper.entityToModel(x))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
				
	}
	
	@GetMapping("/{accountType}/{currency}")
	public Mono<ResponseEntity<AccountTemplateModel>> getByAccountType(@PathVariable String accountType,@PathVariable String currency){
		log.info("getById executed {}",accountType);
		Mono<AccountTemplate> response=accountTemplateRepository.findByAccountTypeAndCurrency(accountType,currency);
		return response.map(x->accountTemplateMapper.entityToModel(x))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
				
	}
	
	@PostMapping()
	public Mono<ResponseEntity<AccountTemplateModel>> create(@Valid @RequestBody AccountTemplateModel request){
		log.info("created executed {}", request);
		//personalAccount.setUserRegisteringId(System.getProperty("user.name"));
		//personalAccount.setOpeningDate(LocalDate.now());
		return accountTemplateRepository.save(accountTemplateMapper.modelToEntity(request))
				.map(x-> accountTemplateMapper.entityToModel(x))
				.flatMap(x->Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s",name,port,"AccountTemplate", x.getId())))
						.body(x)));
	}
	
	/*  @PutMapping("/{id}")
	  public Mono<ResponseEntity<AccountTemplateModel>> updateById(@PathVariable String id, @Valid @RequestBody AccountTemplateModel request){
		 // log.info("updateById executed {}:{}" ,id, request);
		  return AccountTemplateRepository.save(id, AccountTemplateMapper.modelToEntity(request))
				  .map(x -> AccountTemplateMapper.entityToModel(x))
				  .flatMap(y-> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "PersonalAccount", y.getId())))
				  .body(y)))
				  .defaultIfEmpty(ResponseEntity.badRequest().build());
	  }*/
	
	    @DeleteMapping("/{id}")
	    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
	    	log.info("deleteById executed {}", id);
	    	return 
	    			accountTemplateRepository.deleteById(id)
	    			.map(x-> ResponseEntity.ok().<Void>build())
	    			.defaultIfEmpty(ResponseEntity.notFound().build());
	    }
	    
	
	    

}
