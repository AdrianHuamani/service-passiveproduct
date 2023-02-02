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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.passiveProduct.domain.Commision;
import com.bootcamp.passiveProduct.repository.CommisionRepository;
import com.bootcamp.passiveProduct.web.mapper.CommisionMapper;
import com.bootcamp.passiveProduct.web.mapper.PersonalAccountTransactionMapper;
import com.bootcamp.passiveProduct.web.model.CommisionModel;
import com.bootcamp.passiveProduct.web.model.PersonalAccountModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/commision")
public class CommisionController {
	
	@Value("${spring.application.name}")
	String name;
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private CommisionRepository commisionRepository;
	
	@Autowired
	private CommisionMapper commisionMapper;
	
	@GetMapping()
	public Mono<ResponseEntity<Flux<CommisionModel>>> getAll(){
		log.info("Get All executed");
		return Mono.just(ResponseEntity.ok()
				.body(commisionRepository.findAll()
						.map(x ->commisionMapper.entityToModel(x))));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<CommisionModel>> getById(@PathVariable String id){
		log.info("getById executed {}", id);
		Mono<Commision> response=commisionRepository.findById(id);
		return response.map(x->commisionMapper.entityToModel(x))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
				
	}
	
	@GetMapping("/{type}/{accountType}")
	public Mono<ResponseEntity<CommisionModel>> getById(@PathVariable String type, @PathVariable String accountType){
		log.info("getById executed {}", type, accountType);
		Mono<Commision> response=commisionRepository.findByTypeAndAccountType(type, accountType);
		return response.map(x->commisionMapper.entityToModel(x))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
				
	}
	
	@PostMapping()
	public Mono<ResponseEntity<CommisionModel>> create(@Valid @RequestBody CommisionModel request){
		log.info("created executed {}", request);
		return commisionRepository.save(commisionMapper.modelToEntity(request))
				.map(x-> commisionMapper.entityToModel(x))
				.flatMap(x->Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s",name,port,"Commision", x.getId())))
						.body(x)));
	}
	
	/*  @PutMapping("/{id}")
	  public Mono<ResponseEntity<CommisionModel>> updateById(@PathVariable String id, @Valid @RequestBody CommisionModel request){
		 // log.info("updateById executed {}:{}" ,id, request);
		  return commisionRepository.save(id, commisionMapper.modelToEntity(request))
				  .map(x -> commisionMapper.entityToModel(x))
				  .flatMap(y-> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "PersonalAccount", y.getId())))
				  .body(y)))
				  .defaultIfEmpty(ResponseEntity.badRequest().build());
	  }*/
	
	    @DeleteMapping("/{id}")
	    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
	    	log.info("deleteById executed {}", id);
	    	return 
	    			commisionRepository.deleteById(id)
	    			.map(x-> ResponseEntity.ok().<Void>build())
	    			.defaultIfEmpty(ResponseEntity.notFound().build());
	    }
	    
	
	    

}
