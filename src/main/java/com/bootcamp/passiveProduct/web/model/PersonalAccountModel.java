package com.bootcamp.passiveProduct.web.model;


import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PersonalAccountModel {

	@JsonIgnore
	private String id;
	
	@NotBlank(message="accountType cannot be null or empty")
	private String accountType;
	
	@NotBlank(message="accountNumber cannot be null or empty")
	private String accountNumber;
	
	@JsonIgnore
	private String cci;
	
	@JsonIgnore
	private Double availableBalance;
	
	@JsonIgnore
	private Double balance;
	
	@JsonIgnore
	private String userRegisteringId;
	
	@NotBlank(message="currency cannot be null or empty")
	private String currency;
	
	@JsonIgnore
	private Integer movementQuantity;
	
	@JsonIgnore
	private Integer movementQuantityMax;
	
	@JsonIgnore
	private String movementFrecuency;
	
	@NotBlank(message="identityNumberClient cannot be null or empty")
    private String identityNumberClient; 
	
	@NotBlank(message="identityTypeClient  cannot be null or empty")
    private String identityTypeClient; 
	
	@JsonIgnore
	private LocalDate openingDate;

	public PersonalAccountModel() {
		this.userRegisteringId=System.getProperty("user.name");
		this.openingDate=LocalDate.now();
	}
}
