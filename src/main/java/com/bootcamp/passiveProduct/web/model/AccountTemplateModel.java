package com.bootcamp.passiveProduct.web.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountTemplateModel {

	@JsonIgnore
	private String id;
	
	@NotBlank(message="accountType cannot be null or empty")
	private String accountType; 
	
	private Double availableBalance; //
	
	private Double balance;
	
	@JsonIgnore
	private String userRegisteringId;
	
	@NotNull
	private String currency;
	
	private Integer movementQuantityMax;
	
	private String movementFrecuency;
	
	private LocalDate movementDate;
	@JsonIgnore
	private LocalDate createDate;

	public AccountTemplateModel() {
		this.userRegisteringId=System.getProperty("user.name");
		this.createDate=LocalDate.now();
	}
	
}
